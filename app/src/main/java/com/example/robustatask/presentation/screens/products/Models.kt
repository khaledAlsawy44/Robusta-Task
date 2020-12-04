package com.example.robustatask.presentation.screens.products

import com.example.robustatask.domain.ProductDescription
import com.example.robustatask.domain.ProductId
import com.example.robustatask.domain.ProductName

sealed class ProductsState {
    object InitialState : ProductsState()
    object Loading : ProductsState()
    data class Error(val errorMessage: String?, val searchKey: String) : ProductsState()
    data class Empty(val searchKey: String) : ProductsState()

    data class Success(
        val products: List<ProductUi>,
        val searchKey: String,
        val isSearching: Boolean
    ) : ProductsState()
}

sealed class ProductsActions {
    data class Search(val searchKey: String) : ProductsActions()
    object ShowMore : ProductsActions()
    object TryAgain : ProductsActions()
}

sealed class ProductsEffects {
    data class ShowSnakeBar(val message: String?) : ProductsEffects()
}

data class ProductUi(
    val searchKey: String,
    val productId: ProductId,
    val productName: ProductName,
    val productDescription: ProductDescription
)