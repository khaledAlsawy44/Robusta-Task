package com.example.robustatask.presentation.screens.home

import androidx.core.view.isVisible
import com.example.robustatask.R
import com.example.robustatask.databinding.HomeFragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

fun HomeFragmentBinding.showInitialState() {
    searchTipTv.isVisible = true
    loadingPb.isVisible = false
    productsRv.isVisible = false

    errorIv.isVisible = false
    errorMessageTv.isVisible = false
    errorParentView.isVisible = false
    errorTryAgain.isVisible = false

    searchTipTv.text = searchTipTv.context.getString(R.string.search_tip_message)

}

fun HomeFragmentBinding.showLoadingState() {
    loadingPb.isVisible = true
    searchTipTv.isVisible = false
    productsRv.isVisible = false

    errorIv.isVisible = false
    errorMessageTv.isVisible = false
    errorParentView.isVisible = false
    errorTryAgain.isVisible = false
}

fun HomeFragmentBinding.showErrorState() {
    loadingPb.isVisible = false
    searchTipTv.isVisible = false
    productsRv.isVisible = false
    errorIv.isVisible = true
    errorMessageTv.isVisible = true
    errorParentView.isVisible = true
    errorTryAgain.isVisible = true
}

fun HomeFragmentBinding.showEmptyState(searchKey: String) {
    loadingPb.isVisible = false
    productsRv.isVisible = false
    searchTipTv.isVisible = true

    errorIv.isVisible = false
    errorMessageTv.isVisible = false
    errorParentView.isVisible = false
    errorTryAgain.isVisible = false

    searchTipTv.text = searchTipTv.context.getString(R.string.no_data_for_search_result, searchKey)
}

@ExperimentalCoroutinesApi
fun HomeFragmentBinding.showSuccessState(
    state: ProductsState.Success,
    controller: ProductsController
) {
    productsRv.isVisible = true
    loadingPb.isVisible = false
    searchTipTv.isVisible = false

    controller.setData(state)
}

