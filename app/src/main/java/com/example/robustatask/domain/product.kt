package com.example.robustatask.domain

inline class ProductName(val name: String)
inline class ProductDescription(val description: String)
inline class ProductId(val id: Int)

data class Product(
    val productId: ProductId,
    val productName: ProductName,
    val productDescription: ProductDescription
)