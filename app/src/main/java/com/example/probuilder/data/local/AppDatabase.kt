package com.example.probuilder.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.probuilder.domain.model.Invoice
import com.example.probuilder.domain.model.InvoiceItem
import com.example.probuilder.domain.repository.InvoiceDao

@Database(entities = [InvoiceItem::class, Invoice::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun invoiceItemDao(): InvoiceDao

    companion object {
        // Volatile means writes to this field are immediately made visible to other threads.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Function to get the singleton instance of the database
        fun getDatabase(context: Context): AppDatabase {
            // If the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "your_database_name" // Database name
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this example.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // Return instance
                instance
            }
        }
    }
}