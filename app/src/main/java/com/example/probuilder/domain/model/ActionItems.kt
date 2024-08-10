package com.example.probuilder.domain.model

data class ActionItems(
    val text: String,
    val onClick: () -> Unit,
    val icon: Unit? = null
)