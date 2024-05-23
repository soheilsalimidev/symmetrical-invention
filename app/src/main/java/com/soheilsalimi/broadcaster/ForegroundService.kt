package com.soheilsalimi.broadcaster

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class ForegroundService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start(intent.getIntExtra("state" , 0));
            Actions.STOP.toString() -> stop();
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(state: Int) {
        Log.d("TAG", "start: $state")
        val notification = NotificationCompat.Builder(
            this, "internet_channel"
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(
                when (state) {
                    0 -> "not connected"
                    1 -> "mobile connected"
                    else -> "wifi"
                }
            )
            .build()
        startForeground(10, notification)
    }

    private fun stop() {
        stopSelf()
    }

    enum class Actions {
        START, STOP
    }
}
