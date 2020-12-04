package com.example.robustatask.presentation.screens.home

import com.example.robustatask.presentation.flowredux.StateViewModel

class ProductsViewModel (
    stateMachine: ProductsStateMachine
) : StateViewModel<ProductsState, ProductsActions, ProductsEffects>(stateMachine)