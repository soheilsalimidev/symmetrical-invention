package com.soheilsalimi.broadcaster

import android.annotation.SuppressLint
import android.content.Context
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


class saveLogs {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun AppendLog(context: Context, text: String) {
            val path = context.getExternalFilesDir("")
            val logFile = File(path, "log.txt")
            val formatter = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS | ")
            val log_line: String = formatter.format(Date()) + text
            try {
                val buf = BufferedWriter(FileWriter(logFile, true))
                buf.append(log_line)
                buf.newLine()
                buf.close()
            } catch (_: IOException) {
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun getLogs(context: Context): MutableList<String> {
            val path = context.getExternalFilesDir("")
            val list: MutableList<String> = ArrayList()
            try {
                val logFile = File(path, "log.txt").inputStream()
                logFile.bufferedReader().useLines {  it.forEach { line ->
                    list.add(line)
                } }
            } catch (_: IOException) {
            }
            return list
        }

    }

}