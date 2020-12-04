package com.example.robustatask.presentation.screens.home

import arrow.core.Either
import com.example.robustatask.domain.IProductsRepository
import com.example.robustatask.presentation.flowredux.StateMachine
import com.freeletics.flowredux.GetState
import com.freeletics.flowredux.dsl.SetState

class ProductsStateMachine(
    private val repo: IProductsRepository
) : StateMachine<ProductsState, ProductsActions, ProductsEffects>(
    ProductsState.InitialState
) {

    init {

        spec {
            inState(isInState = { true }) {
                on<ProductsActions.Search>(block = ::search)
            }
            inState<ProductsState.Success> {
                on(block = ::showMore)
            }

            inState<ProductsState.Error> {
                on<ProductsActions.TryAgain> { _, getState, setState ->
                    val currentState = getState()
                    if (currentState is ProductsState.Error) {
                        setState { ProductsState.Loading }
                        dispatch(ProductsActions.Search(currentState.searchKey))
                    }

                }
            }
        }
    }


    private suspend fun search(
        action: ProductsActions.Search,
        getState: GetState<ProductsState>,
        setState: SetState<ProductsState>
    ) {
        val currentState = getState()
        if (currentState !is ProductsState.Success || (currentState.searchKey != action.searchKey)) {
            if (action.searchKey.isEmpty()) {
                setState { ProductsState.InitialState }
            } else {
                setState { ProductsState.Loading }
                val searchResult = repo.fetchProducts(action.searchKey, 1)
                setState {
                    when (searchResult) {
                        is Either.Right ->
                            if (searchResult.b.isEmpty())
                                ProductsState.Empty(action.searchKey)
                            else
                                ProductsState.Success(
                                    products = searchResult.b.map { it.toProductUi(action.searchKey) },
                                    searchKey = action.searchKey,
                                    isSearching = false
                                )
                        is Either.Left -> ProductsState.Error(
                            searchResult.a.message,
                            action.searchKey
                        )
                    }
                }
            }
        }
    }


    private suspend fun showMore(
        action: ProductsActions.ShowMore,
        getState: GetState<ProductsState>,
        setState: SetState<ProductsState>
    ) {
        val currentState = getState()
        if (currentState is ProductsState.Success) {
            setState {
                currentState.copy(
                    isSearching = true
                )
            }

            val result = repo.fetchProducts(
                searchKey = currentState.searchKey,
                page = 2
            )
            when (result) {
                is Either.Left -> setState {
                    ProductsState.Error(
                        result.a.message,
                        currentState.searchKey
                    )
                }
                is Either.Right -> setState {
                    ProductsState.Success(
                        result.b.map { it.toProductUi(currentState.searchKey) },
                        searchKey = currentState.searchKey,
                        isSearching = false
                    )
                }
            }
        }

    }

}