package com.example.probuilder.presentation.screen.categories.services_screen

sealed class ServicesScreenEvent {
    data class OpenDetails(val jobId: String) : ServicesScreenEvent()
    data class Hide(val jobId: String) : ServicesScreenEvent()
}
