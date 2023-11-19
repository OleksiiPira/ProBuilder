package com.example.probuilder.presentation.screen.price_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.probuilder.common.Resource
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.PriceList
import com.example.probuilder.domain.use_case.get_feed.GetPriceListUseCase
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PriceListViewModel @Inject constructor(
    private val getPriceListUseCase: GetPriceListUseCase
): ViewModel() {
    private val _state = mutableStateOf("") //mutableStateOf(PriceListState())
    val state = _state

    init {
        getPriceList()
    }

    private fun getPriceList() {
        getPriceListUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = "Test text"//PriceListState(
//                        priceList = result.data ?: PriceList(emptyList())
//                    )
                }
                is Resource.Error -> {
                    _state.value = result.message.toString()
//                        PriceListState(
//                        error = result.message ?: "An error occured"
//                    )
                }
                is Resource.Loading -> {
                    _state.value = "Loading"
//                        PriceListState(
//                        isLoading = true
//                    )
                }
            }
        }
    }

    fun getPriceListByCategoryId(categoryId: String): Category {
        return Category()//state.value.priceList.categories.find { category -> category.id == categoryId } ?: Category()
    }
}