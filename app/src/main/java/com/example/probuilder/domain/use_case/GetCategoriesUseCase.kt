package com.example.probuilder.domain.use_case

import com.example.probuilder.common.Resource
import com.example.probuilder.data.remote.dto.AppRepositoryImpl
import com.example.probuilder.data.remote.dto.toRow
import com.example.probuilder.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
class GetCategoriesUseCase @Inject constructor(
    private val repository: AppRepositoryImpl
) {
    operator fun invoke(): Flow<Resource<List<Category>>> = flow {
        try {
            emit(Resource.Loading()) // show loading
            val feed = repository.getRows().map { i -> i.toRow() }
            emit(Resource.Success(feed))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}
