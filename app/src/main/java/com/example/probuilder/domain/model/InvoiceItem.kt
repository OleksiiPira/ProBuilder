package com.example.probuilder.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "invoice_items")
data class InvoiceItem(
    val name: String = "",
    val invoiceId: String = "",
    val quantity: Int = 0,
    val unit: String = "",
    val pricePerUnit: Double = 0.0,
    val totalPrice: Double = 0.0,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
)
