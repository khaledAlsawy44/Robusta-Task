package com.example.robustatask.presentation.screens.home

import androidx.core.view.isVisible
import com.example.robustatask.databinding.HomeFragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

fun HomeFragmentBinding.showInitialState() {
    searchTipTv.isVisible = true
    loadingPb.isVisible = false
    productsRv.isVisible = false
}

fun HomeFragmentBinding.showLoadingState() {
    loadingPb.isVisible = true
    searchTipTv.isVisible = false
    productsRv.isVisible = false
}

fun HomeFragmentBinding.showErrorState() {
    loadingPb.isVisible = false
    searchTipTv.isVisible = false
    productsRv.isVisible = false
}

@ExperimentalCoroutinesApi
fun HomeFragmentBinding.showSuccessState(
    state: ProductsState.Success,
    controller: ProductsController
) {
    productsRv.isVisible = true
    loadingPb.isVisible = false
    searchTipTv.isVisible = false

    controller.setData(state.products)
}

