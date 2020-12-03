package com.example.robustatask.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.robustatask.domain.IProductsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class HomeViewModel(
    private val repo: IProductsRepository
) : ViewModel() {

//    private val _uiStateLiveData = MutableLiveData<String>()
//    val uiStateLiveData: LiveData<String> = _stateLiveData


    // play with the new stateFlow
    private val _uiState = MutableStateFlow<ProductsState>(ProductsState.InitialState)
    val uiState: StateFlow<ProductsState> = _uiState

    fun dispatch(action: ProductsActions) {
        when (action) {
            is ProductsActions.Search -> onSearch(action.searchKey)
        }
    }

    private fun onSearch(searchKey: String) {
        viewModelScope.launch {
            if (searchKey.isEmpty()) {
                _uiState.value = ProductsState.InitialState
            } else {
                _uiState.value = ProductsState.Loading
                when (val result = repo.fetchProducts(searchKey)) {
                    is Either.Left -> _uiState.value = ProductsState.Error(result.a.message)
                    is Either.Right -> _uiState.value = ProductsState.Success(result.b)
                }
            }

        }
    }

}