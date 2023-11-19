package com.example.probuilder.domain.use_case.get_feed

import com.example.probuilder.common.Resource
import com.example.probuilder.data.remote.dto.toPriceList
import com.example.probuilder.domain.model.PriceList
import com.example.probuilder.domain.repository.PriceListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPriceListUseCase @Inject constructor(
    private val repository: PriceListRepository
) {
    operator fun invoke(): Flow<Resource<PriceList>> = flow {
        try {
            emit(Resource.Loading()) // show loading
            val feed = repository.getFeed().toPriceList()
            emit(Resource.Success(feed))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}