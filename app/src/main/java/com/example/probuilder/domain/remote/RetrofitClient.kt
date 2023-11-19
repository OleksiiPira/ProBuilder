package com.example.probuilder.domain.remote

import com.example.probuilder.data.remote.PriceListApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://240e06c3-c1ef-4755-bb4b-59a2b64cd78b.mock.pstmn.io/v1/content/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val jobService: PriceListApi by lazy {
        retrofit.create(PriceListApi::class.java)
    }
}
