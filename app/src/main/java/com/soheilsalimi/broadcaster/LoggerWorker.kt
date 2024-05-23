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

    override suspend fun doWork(): Result {
        try {
            val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            val bluetoothAdapter = bluetoothManager.adapter
            Log.i("worker_airplane", "bluetooth: "+   bluetoothAdapter?.isEnabled + " airplane is : " + isAirplaneModeOn(context))
            saveLogs.AppendLog(context , "bluetooth: "+   bluetoothAdapter?.isEnabled + " airplane is : " + isAirplaneModeOn(context))
        }catch (e:Error){
            Log.i("worker_airplane", "approve permissions")
            saveLogs.AppendLog(context , "worker failed because of no permission")
        }
             return Result.success()
    }

    private fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0
    }

}