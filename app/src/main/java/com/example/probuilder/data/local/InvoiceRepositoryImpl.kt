package com.example.probuilder.data.local

import com.example.probuilder.domain.model.Invoice
import com.example.probuilder.domain.model.InvoiceItem
import com.example.probuilder.domain.repository.InvoiceDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvoiceRepositoryImpl @Inject constructor(
    private val dao: InvoiceDao
) {
    suspend fun upsertInvoiceItem(invoiceItem: InvoiceItem) { dao.upsertItem(invoiceItem) }
    fun getAllItemsByInvoiceId(invoiceId: String): Flow<List<InvoiceItem>> { return dao.getAllItemsByInvoiceId(invoiceId) }

    suspend fun upsertInvoice(invoice: Invoice) { dao.upsertInvoice(invoice) }
    fun getInvoices(): Flow<List<Invoice>> { return dao.getInvoices() }
    fun getInvoiceById(id: String): Flow<List<Invoice>> { return dao.getInvoiceById(id = id) }

    suspend fun deleteById(invoice: Invoice) { dao.deleteInvoice(invoice) }
}
