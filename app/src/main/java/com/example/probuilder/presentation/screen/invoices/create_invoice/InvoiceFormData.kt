package com.example.probuilder.presentation.screen.invoices.create_invoice

import com.example.probuilder.domain.model.Invoice
import java.util.UUID

data class InvoiceFormData(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val date: String = "",
    val lastPaymentDate: String = "",
    val invoiceNumber: String = "",

    // seller
    val performerName: String = "", //Ваша компанія
    val performerPersonNumber: String = "",
    val performerAddress: String = "",
    val performerPhone: String = "",
    val performerEmail: String = "",
    val performerIban: String = "",

    // client
    val clientName: String = "", //Ваша компанія
    val clientPersonNumber: String = "",
    val clientAddress: String = "",
    val clientPhone: String = "",
    val clientEmail: String = "",
)

fun InvoiceFormData.toInvoice(invoiceFormData: InvoiceFormData): Invoice {
    return Invoice(
        id = invoiceFormData.id,
        name = invoiceFormData.name,
        date = invoiceFormData.date,
        finalPaymentDate = invoiceFormData.lastPaymentDate,
        invoiceNumber = invoiceFormData.invoiceNumber,

        performerName = invoiceFormData.performerName,
        performerPersonNumber = invoiceFormData.performerPersonNumber,
        performerAddress = invoiceFormData.performerAddress,
        performerPhone = invoiceFormData.performerPhone,
        performerEmail = invoiceFormData.performerEmail,
        performerIban = invoiceFormData.performerIban,

        clientName = invoiceFormData.clientName,
        clientPersonNumber = invoiceFormData.clientPersonNumber,
        clientAddress = invoiceFormData.clientAddress,
        clientPhone = invoiceFormData.clientPhone,
        clientEmail = invoiceFormData.clientEmail
    )
}

fun InvoiceFormData.toInvoiceState(invoice: Invoice): InvoiceFormData {
    return InvoiceFormData(
        id = invoice.id,
        name = invoice.name,
        date = invoice.date,
        lastPaymentDate = invoice.finalPaymentDate,
        invoiceNumber = invoice.invoiceNumber,

        performerName = invoice.performerName,
        performerPersonNumber = invoice.performerPersonNumber,
        performerAddress = invoice.performerAddress,
        performerPhone = invoice.performerPhone,
        performerEmail = invoice.performerEmail,
        performerIban = invoice.performerIban,

        clientName = invoice.clientName,
        clientPersonNumber = invoice.clientPersonNumber,
        clientAddress = invoice.clientAddress,
        clientPhone = invoice.clientPhone,
        clientEmail = invoice.clientEmail,
    )
}
