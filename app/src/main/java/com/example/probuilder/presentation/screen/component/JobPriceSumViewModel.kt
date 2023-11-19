//package com.example.probuilder.component
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.probuilder.model.JobPriceSum
//
//
//class JobPriceSumViewModel : ViewModel() {
//    private val _jobPriceSumList = MutableLiveData(mutableMapOf<String, JobPriceSum>())
//    val jobPriceSumList: LiveData<MutableMap<String, JobPriceSum>> = _jobPriceSumList
//    private var id = 0
//
//    fun addItem(jobPriceSum: JobPriceSum) {
//        _jobPriceSumList
//    }
//}