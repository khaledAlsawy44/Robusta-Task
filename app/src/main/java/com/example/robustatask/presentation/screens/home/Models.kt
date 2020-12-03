package com.example.robustatask.presentation.screens.home

import com.example.robustatask.domain.Product

sealed class ProductsState {
    object InitialState : ProductsState()
    object Loading : ProductsState()
    data class Error(val errorMessage: String?) : ProductsState()
    data class Success(val products: List<Product>) : ProductsState()
}

sealed class ProductsActions {
    data class Search(val searchKey: String) : ProductsActions()
}