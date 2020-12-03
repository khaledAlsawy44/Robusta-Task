package com.example.robustatask.data.source.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductsResponse(
    val productId: Int? = null,
    val productName: String? = null
)
