package com.example.robustatask.data.repository

import arrow.core.Either
import com.example.robustatask.AppFailure
import com.example.robustatask.data.source.ProductsRemoteDataSource
import com.example.robustatask.domain.IProductsRepository
import com.example.robustatask.domain.Product

class ProductsRepository(
    private val remote: ProductsRemoteDataSource
) : IProductsRepository {
    override suspend fun fetchProducts(searchKey: String): Either<AppFailure, List<Product>> {
        return remote.fetchProducts(searchKey)
    }
}