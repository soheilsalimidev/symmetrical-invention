package com.soheilsalimi.broadcaster


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.soheilsalimi.broadcaster.ui.theme.BroadCasterTheme
import java.time.Duration
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BroadCasterTheme {
                LaunchedEffect(key1 = Unit) {
                    val workRequest = PeriodicWorkRequestBuilder<LoggerWorker>(
                        repeatInterval = 2,
                        repeatIntervalTimeUnit = TimeUnit.MINUTES,
                    ).setBackoffCriteria(
                        backoffPolicy = BackoffPolicy.LINEAR,
                        duration = Duration.ofSeconds(15)
                    ).build()

                    val workManager = WorkManager.getInstance(applicationContext)
                    workManager.enqueueUniquePeriodicWork(
                        "myWorkerName",
                        ExistingPeriodicWorkPolicy.KEEP,
                        workRequest
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BroadCasterTheme {
        Greeting("Android")
    }
}