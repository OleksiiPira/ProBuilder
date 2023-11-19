package com.example.probuilder.data.remote.dto

import com.example.probuilder.domain.model.Category

data class CategoryDto(
    val id: String,
    val title: String,
    val tags: List<String>,
    val jobs: List<JobDto>
)

fun CategoryDto.toCategory(): Category {
    return Category(
        id = id,
        title = title,
        tags = tags,
        jobs = jobs.map { jobDto -> jobDto.toJob() }
    )
}