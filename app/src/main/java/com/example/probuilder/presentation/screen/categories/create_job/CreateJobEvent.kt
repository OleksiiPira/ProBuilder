package com.example.probuilder.presentation.screen.categories.create_job

sealed interface CreateJobEvent {
    data class SetJobName(val name: String): CreateJobEvent
    data class SetMeasureUnit(val measureUnit: String): CreateJobEvent
    data class SetPricePerUnit(val pricePreUnit: String): CreateJobEvent
}
