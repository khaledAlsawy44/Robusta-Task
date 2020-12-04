package com.example.robustatask.domain

import arrow.core.Either
import com.example.robustatask.AppFailure

interface IProductsRepository {
    suspend fun fetchProducts(searchKey: String, page: Int): Either<AppFailure, List<Product>>
}