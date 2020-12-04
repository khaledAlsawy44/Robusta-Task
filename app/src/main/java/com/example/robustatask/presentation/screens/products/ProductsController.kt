package com.example.robustatask.presentation.screens.products

import com.airbnb.epoxy.TypedEpoxyController
import com.example.robustatask.presentation.components.productsView
import com.example.robustatask.presentation.components.scrollToTopView
import com.example.robustatask.presentation.components.showMoreView
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ProductsController(
    private val onClicked: (product: ProductUi) -> Unit,
    private val onShowMoreClicked: () -> Unit,
    private val scrollToTopClicked: () -> Unit
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

        if (state.products.size > 10)
            scrollToTopView {
                id(-2)
                scrollToTopListener {
                    scrollToTopClicked()
                }
            }
    }
}