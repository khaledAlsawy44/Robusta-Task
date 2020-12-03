package com.example.robustatask.data.source.services

import com.example.robustatask.ApiResponse
import com.example.robustatask.data.source.entities.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsService {
    @GET("products/{search_key}-{page}")
    suspend fun fetchProducts(
        @Path("search_key") key: String,
        @Path("page") page: Int
    ): ApiResponse<List<ProductsResponse>>
}