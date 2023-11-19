package com.example.probuilder.data.remote.dto

import com.example.probuilder.domain.model.Job

data class JobDto(
    val title: String,
    val price: Map<String, Int>,
    val measure: String,
    val surface: String,
)

fun JobDto.toJob(): Job {
    return Job(
        title = title,
        price = price,
        measure = measure,
        surface = surface
    )
}