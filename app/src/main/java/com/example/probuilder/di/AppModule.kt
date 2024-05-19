package com.example.probuilder.di

import android.content.Context
import com.example.probuilder.data.local.AppDatabase
import com.example.probuilder.data.local.CategoriesRepository
import com.example.probuilder.data.local.InvoiceRepositoryImpl
import com.example.probuilder.data.local.ServiceRepository
import com.example.probuilder.data.remote.AppApi
import com.example.probuilder.data.remote.dto.AppRepositoryImpl
import com.example.probuilder.domain.repository.AppRepository
import com.example.probuilder.domain.repository.CategoriesDao
import com.example.probuilder.domain.repository.InvoiceDao
import com.example.probuilder.domain.repository.ServiceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppApi(): AppApi {
        return Retrofit.Builder()
            .baseUrl(com.example.probuilder.common.Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppRepository(api: AppApi): AppRepository {
        return AppRepositoryImpl(api)
    }

    @Provides
    fun provideYourDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(context = appContext)
    }

    @Provides
    fun provideInvoiceDao(database: AppDatabase): InvoiceDao {
        return database.invoiceItemDao()
    }
    @Provides
    @Singleton
    fun provideInvoiceRepositoryImpl(dao: InvoiceDao): InvoiceRepositoryImpl {
        return InvoiceRepositoryImpl(dao)
    }

    @Provides
    fun provideCategoriesDao(database: AppDatabase): CategoriesDao {
        return database.categoryDao()
    }

    @Provides
    @Singleton
    fun provideServiceRepositoryImpl(dao: ServiceDao): ServiceRepository {
        return ServiceRepository(dao)
    }

    @Provides
    fun provideServiceDao(database: AppDatabase): ServiceDao {
        return database.serviceDao()
    }

    fun provideCategoriesRepository(dao: CategoriesDao): CategoriesRepository {
        return CategoriesRepository(dao)
    }

}