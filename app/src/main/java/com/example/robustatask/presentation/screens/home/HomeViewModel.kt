package com.example.robustatask.presentation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.robustatask.domain.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeViewModel(
) : ViewModel() {

    private val _searchLiveData = MutableLiveData<String>()
    val searchLiveData: LiveData<String> = _searchLiveData

    fun search(searchKey: String) {
        _searchLiveData.value = searchKey
    }
}

fun Product.toProductUi(searchKey: String): ProductUi {
    return ProductUi(
        searchKey, productId, productName, productDescription
    )
}
