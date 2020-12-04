package com.example.robustatask.presentation.screens.products

import androidx.core.view.isVisible
import com.example.robustatask.R
import com.example.robustatask.databinding.ProductsFragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

fun ProductsFragmentBinding.showInitialState() {
    searchTipTv.isVisible = true
    searchIv.isVisible = true
    loadingPb.isVisible = false
    productsRv.isVisible = false

    errorIv.isVisible = false
    errorMessageTv.isVisible = false
    errorParentView.isVisible = false
    errorTryAgain.isVisible = false

    searchTipTv.text = searchTipTv.context.getString(R.string.search_tip_message)

}

fun ProductsFragmentBinding.showLoadingState() {
    loadingPb.isVisible = true
    searchTipTv.isVisible = false
    searchIv.isVisible = false
    productsRv.isVisible = false

    errorIv.isVisible = false
    errorMessageTv.isVisible = false
    errorParentView.isVisible = false
    errorTryAgain.isVisible = false
}

fun ProductsFragmentBinding.showErrorState() {
    loadingPb.isVisible = false
    searchTipTv.isVisible = false
    searchIv.isVisible = false
    productsRv.isVisible = false
    errorIv.isVisible = true
    errorMessageTv.isVisible = true
    errorParentView.isVisible = true
    errorTryAgain.isVisible = true
}

fun ProductsFragmentBinding.showEmptyState(searchKey: String) {
    loadingPb.isVisible = false
    productsRv.isVisible = false
    searchTipTv.isVisible = true
    searchIv.isVisible = false

    errorIv.isVisible = false
    errorMessageTv.isVisible = false
    errorParentView.isVisible = false
    errorTryAgain.isVisible = false

    searchTipTv.text = searchTipTv.context.getString(R.string.no_data_for_search_result, searchKey)
}

@ExperimentalCoroutinesApi
fun ProductsFragmentBinding.showSuccessState(
    state: ProductsState.Success,
    controller: ProductsController
) {
    productsRv.isVisible = true
    loadingPb.isVisible = false
    searchTipTv.isVisible = false
    searchIv.isVisible = false

    controller.setData(state)
}

