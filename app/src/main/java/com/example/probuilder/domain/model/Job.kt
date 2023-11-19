package com.example.probuilder.domain.model

data class Job(
    val title: String = "",
    val price: Map<String, Int> = emptyMap(),
    val measure: String = "",
    val surface: String = "",
)