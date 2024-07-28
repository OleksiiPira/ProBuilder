package com.example.probuilder.presentation.screen.categories.create_service

import com.example.probuilder.domain.model.Category

sealed interface CreateServiceEvent {
    object SaveService: CreateServiceEvent
    data class SetServiceName(val name: String): CreateServiceEvent
    data class UpdateCurrentCategory(val category: Category): CreateServiceEvent
    data class SetMeasureUnit(val measureUnit: String): CreateServiceEvent
    data class SetPricePerUnit(val pricePreUnit: String): CreateServiceEvent
}
