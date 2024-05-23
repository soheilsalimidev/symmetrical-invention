package com.soheilsalimi.broadcaster

import android.bluetooth.BluetoothManager
import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class LoggerWorker constructor(
    private val context: Context,
    workerParameters: WorkerParameters,
): CoroutineWorker(context, workerParameters) {

    init {

    }
    override suspend fun doWork(): Result {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter
        Log.i("worker_airplane", "bluetooth: "+   bluetoothAdapter?.isEnabled + " airplane is : " + isAirplaneModeOn(context))

        return Result.success()
    }

    private fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0
    }

}