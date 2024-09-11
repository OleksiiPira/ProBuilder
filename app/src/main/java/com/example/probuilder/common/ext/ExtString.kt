package com.example.probuilder.common.ext

import java.text.NumberFormat
import java.util.Locale


fun Int.toUAH(): String {
    val formatter = NumberFormat.getNumberInstance(Locale("uk", "UA"))
    return formatter.format(this) + " грн"
}