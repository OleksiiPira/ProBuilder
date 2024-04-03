package com.example.probuilder.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "invoices")
data class Invoice(
    val name: String = "",
    val date: String = "",
    val finalPaymentDate: String = "",
    val invoiceNumber: String = "",

    // seller
    val performerName: String = "", //Ваша компанія
    val performerPersonNumber: String = "",
    val performerAddress: String = "",
    val performerPhone: String = "",
    val performerEmail: String = "",
    val performerIban: String = "",
    val performerPaymentDetails: String = "",

    // client
    val clientName: String = "", //Ваша компанія
    val clientPersonNumber: String = "",
    val clientAddress: String = "",
    val clientPhone: String = "",
    val clientEmail: String = "",

    val isHided: Boolean = false,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
)
