package com.example.robustatask.data.source

import android.util.Log
import arrow.core.Either
import com.example.robustatask.AppFailure
import com.example.robustatask.data.source.entities.ProductsResponse
import com.example.robustatask.data.source.services.ProductsService
import com.example.robustatask.domain.Product
import com.example.robustatask.domain.ProductId
import com.example.robustatask.domain.ProductName
import com.example.robustatask.mapResponseData
import com.example.robustatask.safe

class ProductsRemoteDataSource(
    private val service: ProductsService
) {
    suspend fun fetchProducts(searchKey: String): Either<AppFailure, List<Product>> {
        val fetchProducts = service.fetchProducts(searchKey, 1)
        Log.d("testsssssss",fetchProducts.toString())

        return safe {
            fetchProducts.mapResponseData { list ->
                list.mapNotNull {
                    it.toProduct()
                }
            }
        }
    }

    private fun ProductsResponse.toProduct(): Product? {
        if (productId == null || productName == null)
            return null
        return Product(
            productId = ProductId(productId),
            productName = ProductName(productName)
        )
    }
}
