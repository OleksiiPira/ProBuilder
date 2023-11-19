package com.example.probuilder.data.remote

import com.example.probuilder.data.remote.dto.PriceListDto
import retrofit2.http.GET

interface PriceListApi {
    @GET("feed")
    suspend fun getFeed(): PriceListDto


//    @GET("str")
//    fun getStr(): Call<String>
}
