package com.example.probuilder.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.remote.dto.toPriceList
import com.example.probuilder.domain.model.Job
import com.example.probuilder.domain.model.PriceList
import com.example.probuilder.domain.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {

    private val _priceList = MutableLiveData<PriceList>()
    val priceList: LiveData<PriceList> = _priceList

    init {
        fetchFeed()
    }

    private fun fetchFeed() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.jobService.getFeed().toPriceList()
                _priceList.postValue(response)
//                val jobResponse = response.execute()
//
//                if (response.isExecuted) {
//                    _priceList.postValue(jobResponse.body())
//                } else {
//                    // Handle the error case
//                }
//                println()
            } catch (e: Exception) {
                // Handle network or other exception
            }
        }
    }


    fun getJobs(categoryId: String): List<Job> {
        return priceList.value?.categories?.find { category -> category.id == categoryId }?.jobs ?: listOf()
    }
//    fun showJobsWithTag(tag: String){
//        val filteredJobs = job1LiveData.value?.filter { it.tags.contains(tag) }?.toTypedArray()
//        this._feed.postValue(filteredJobs)
//    }


}

