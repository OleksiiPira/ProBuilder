package com.example.probuilder.data.remote.dto

import android.util.Log
import com.example.probuilder.domain.model.FeedData
import com.example.probuilder.domain.model.Job

data class FeedDataDto(
    val categories: Map<String, List<JobDto>>
)

fun FeedDataDto.toFeedData(): FeedData{
//    Log.i("AAAAAAAAAA", categories.toString())
//    Log.i("AAAAAAAAAA", categories.toString())
    return FeedData(
        categories = categories.map { (key, listJobDto) -> key to listJobDto.map {
            jobDto -> jobDto.toJob()
            }
        }.toMap()
//        categories = categories.map { categoryDto -> categoryDto.toCategory() }
    )
}