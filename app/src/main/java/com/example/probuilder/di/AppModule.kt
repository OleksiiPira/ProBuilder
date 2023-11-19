package com.example.probuilder.di

import androidx.compose.ui.unit.Constraints
import com.example.probuilder.common.Constants
import com.example.probuilder.data.remote.PriceListApi
import com.example.probuilder.data.remote.dto.PriceListRepositoryImpl
import com.example.probuilder.domain.remote.RetrofitClient
import com.example.probuilder.domain.repository.PriceListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn
object AppModule {

    @Provides
    @Singleton
    fun providePriceListApi(): PriceListApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PriceListApi::class.java)
    }

    @Provides
    @Singleton
    fun providePriceListRepository(api: PriceListApi): PriceListRepository {
        return PriceListRepositoryImpl(api)
    }
}