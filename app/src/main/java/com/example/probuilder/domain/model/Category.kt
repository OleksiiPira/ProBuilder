package com.example.probuilder.domain.model

data class Category(
    val id: String = "",
    val title: String = "",
    val tags: List<String> = emptyList(),
    val jobs: List<Job> = emptyList()
)