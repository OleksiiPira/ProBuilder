package com.example.probuilder.presentation.screen.categories.jobs_screen

sealed class JobsScreenEvent {
    data class OpenDetails(val jobId: String) : JobsScreenEvent()
    data class Hide(val jobId: String) : JobsScreenEvent()
}
