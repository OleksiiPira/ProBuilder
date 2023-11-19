package com.example.probuilder.domain.model

import com.example.probuilder.data.remote.dto.JobDto

data class FeedData(
//    val jobs: Map<String, List<Job>>,
    val categories: Map<String, List<Job>>
)
