package com.example.probuilder.presentation.screen.invoices.add_to_invoice

import com.example.probuilder.domain.model.Invoice

sealed interface AddItemInvoiceEvent {
    data object SaveItem: AddItemInvoiceEvent
    data class SetInvoice(val invoice: Invoice): AddItemInvoiceEvent
    data class SetName(val name: String): AddItemInvoiceEvent
    data class SetQuantity(val quantity: Int): AddItemInvoiceEvent
    data class SetUnit(val unit: String): AddItemInvoiceEvent
    data class SetPricePerUnit(val pricePreUnit: Double): AddItemInvoiceEvent
}