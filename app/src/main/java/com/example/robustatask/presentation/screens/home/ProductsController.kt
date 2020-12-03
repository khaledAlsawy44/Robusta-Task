package com.example.robustatask.presentation.screens.home

import com.airbnb.epoxy.TypedEpoxyController
import com.example.robustatask.domain.Product
import com.example.robustatask.presentation.components.productsView
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ProductsController(
    val onClicked: (product: Product) -> Unit
) : TypedEpoxyController<List<Product>>() {

    override fun buildModels(products: List<Product>) {
        products.forEach {
            productsView {
                id(it.productId.id)
                productData(it)
                itemClickedListener {
                    onClicked(it)
                }
            }
        }
    }

}