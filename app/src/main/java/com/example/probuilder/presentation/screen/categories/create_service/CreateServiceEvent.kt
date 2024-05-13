package com.example.probuilder.presentation.screen.categories.create_service

import com.example.probuilder.domain.model.Invoice

sealed interface CreateServiceEvent {
    object SaveItem: CreateServiceEvent
    data class SetName(val name: String): CreateServiceEvent
    data class SetCategory(val category: String): CreateServiceEvent
    data class SetUnit(val unit: String): CreateServiceEvent
    data class SetPricePerUnit(val pricePreUnit: String): CreateServiceEvent
}
