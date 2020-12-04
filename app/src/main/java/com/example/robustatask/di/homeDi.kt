package com.example.robustatask.di

import com.example.robustatask.data.repository.ProductsRepository
import com.example.robustatask.data.source.ProductsRemoteDataSource
import com.example.robustatask.data.source.services.ProductsService
import com.example.robustatask.domain.IProductsRepository
import com.example.robustatask.presentation.screens.home.HomeViewModel
import com.example.robustatask.presentation.screens.products.ProductsStateMachine
import com.example.robustatask.presentation.screens.products.ProductsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

@ExperimentalCoroutinesApi
val homeModule = module {
    single<IProductsRepository> { ProductsRepository(get()) }
    single { ProductsRemoteDataSource(get()) }
    factory { ProductsStateMachine(get()) }
    viewModel { HomeViewModel() }
    viewModel { ProductsViewModel(get()) }
    single { productService(get()) }
}

fun productService(retrofit: Retrofit): ProductsService {
    return retrofit.create(ProductsService::class.java)
}