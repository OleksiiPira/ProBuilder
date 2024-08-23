package com.example.probuilder.presentation.screen.project.create

sealed interface CreateProjectEvent {
    data class SetName(val title: String): CreateProjectEvent
    data class SetAddress(val address: String): CreateProjectEvent
    data class SetClientName(val clientName: String): CreateProjectEvent
    data class SetPrice(val price: Int): CreateProjectEvent
    data class SetStartDate(val startDate: String): CreateProjectEvent
    data class SetEndDate(val endDate: String): CreateProjectEvent
    data class SetProgress(val progress: Float): CreateProjectEvent
}
