package com.example.probuilder.domain.use_case

import com.example.probuilder.common.Resource
import com.example.probuilder.data.remote.dto.AppRepositoryImpl
import com.example.probuilder.data.remote.dto.toJob
import com.example.probuilder.domain.model.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
class GetServices @Inject constructor(
    private val repository: AppRepositoryImpl
) {
    operator fun invoke(): Flow<Resource<Map<String,List<Service>>>> = flow {
        try {
            emit(Resource.Loading()) // show loading
            val feed = repository.getJobs().mapValues { (_, jobsList) ->
                if (!jobsList.isNullOrEmpty()) {
                    jobsList.map { it.toJob() }
                } else {
                    emptyList()
                }
            }
            emit(Resource.Success(feed))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}
