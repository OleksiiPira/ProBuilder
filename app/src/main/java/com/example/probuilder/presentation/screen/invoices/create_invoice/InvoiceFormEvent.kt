package com.example.probuilder.presentation.screen.invoices.create_invoice

sealed interface InvoiceFormEvent {

    object SaveItem: InvoiceFormEvent
    data class SetInvoiceFormName(val name: String): InvoiceFormEvent
    data class SetDate(val date: String): InvoiceFormEvent
    data class SetFinalPaymentDate(val date: String): InvoiceFormEvent
    data class SetInvoiceFormNumber(val number: String): InvoiceFormEvent

//    performer
    data class SetPerformerName(val name: String): InvoiceFormEvent
    data class SetPerformerPersonNumber(val personNumber: String): InvoiceFormEvent
    data class SetPerformerAddress(val address: String): InvoiceFormEvent
    data class SetPerformerPhone(val phone: String): InvoiceFormEvent
    data class SetPerformerEmail(val email: String): InvoiceFormEvent
    data class SetPerformerIban(val iban: String): InvoiceFormEvent

    // client
    data class SetClientName(val name: String): InvoiceFormEvent
    data class SetClientPersonNumber(val personNumber: String): InvoiceFormEvent
    data class SetClientAddress(val address: String): InvoiceFormEvent
    data class SetClientPhone(val phone: String): InvoiceFormEvent
    data class SetClientEmail(val email: String): InvoiceFormEvent
}
