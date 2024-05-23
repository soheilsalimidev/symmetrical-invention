package com.soheilsalimi.broadcaster

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log


class InterNetBCR : BroadcastReceiver() {

    private fun getConnectionType(context: Context): Int {
        var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            cm.getNetworkCapabilities(cm.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                result = 2
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                result = 1
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                result = 3
            }
        }
        return result
    }

    override fun onReceive(context: Context, intent: Intent) {
        Intent(context, ForegroundService::class.java).also {
            it.action = ForegroundService.Actions.START.toString()
            it.putExtra("state", getConnectionType(context));
            context.startService(it)
        }
        saveLogs.AppendLog(context , "network is " + when (getConnectionType(context)) {
            0 -> "not connected"
            1 -> "mobile connected"
            else -> "wifi"
        })
    }
}