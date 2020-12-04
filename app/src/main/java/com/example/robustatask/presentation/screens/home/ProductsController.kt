package com.example.robustatask.presentation.screens.home

import com.airbnb.epoxy.TypedEpoxyController
import com.example.robustatask.presentation.components.productsView
import com.example.robustatask.presentation.components.showMoreView
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ProductsController(
    private val onClicked: (product: ProductUi) -> Unit,
    private val onShowMoreClicked: () -> Unit
) : TypedEpoxyController<ProductsState.Success>() {

    override fun buildModels(state: ProductsState.Success) {
        state.products.forEach {
            productsView {
                id(it.productId.id)
                productData(it)
                itemClickedListener {
                    onClicked(it)
                }
            }
        }
        if (state.products.size == 10)
            showMoreView {
                id(-1)
                showMoreState(state.isSearching)
                showMoreListener {
                    onShowMoreClicked()
                }
            }
    }
}