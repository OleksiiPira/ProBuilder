package com.example.probuilder.domain.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.probuilder.domain.model.Invoice
import com.example.probuilder.domain.model.InvoiceItem
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {
    @Upsert
    suspend fun upsertItem(invoiceItem: InvoiceItem)

    @Query("SELECT * FROM invoice_items WHERE invoiceId = :invoiceId")//"SELECT * FROM invoice_items WHERE id = :invoiceId")
    fun getAllItemsByInvoiceId(invoiceId: String): Flow<List<InvoiceItem>>

    @Upsert
    suspend fun upsertInvoice(invoice: Invoice)

    @Delete
    suspend fun deleteInvoice(invoice: Invoice)

    @Query("SELECT * FROM invoices")
    fun getInvoices(): Flow<List<Invoice>>

    @Query("SELECT * FROM invoices WHERE id = :id")//"SELECT * FROM invoice_items WHERE id = :invoiceId")
    fun getInvoiceById(id: String): Flow<List<Invoice>>
}
