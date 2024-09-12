package com.example.probuilder.common.ext

import com.google.gson.Gson
import java.text.NumberFormat
import java.util.Locale


fun Int.toUAH(): String {
    val formatter = NumberFormat.getNumberInstance(Locale("uk", "UA"))
    return formatter.format(this) + " грн"
}

fun Double.toMeasure(measure: String): String {
    return "$this $measure"
}

fun Any.toJSON(): String {
    return Gson().toJson(this)
}