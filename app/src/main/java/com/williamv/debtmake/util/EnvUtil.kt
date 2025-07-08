package com.williamv.debtmake.util

import android.content.Context

object EnvUtil {

    fun readEnv(context: Context): Map<String, String> {
        val map = mutableMapOf<String, String>()
        try {
            val inputStream = context.assets.open(".env")
            inputStream.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val trimmed = line.trim()
                    if (trimmed.isNotEmpty() && !trimmed.startsWith("#") && trimmed.contains("=")) {
                        val (key, value) = trimmed.split("=", limit = 2)
                        map[key.trim()] = value.trim()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }
}
