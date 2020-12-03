package com.example.robustatask.di

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://nusratelsunnahacademy.com/nosrah/api/"

val networkModule = module {
    single { okHttpClient(get()) }
    single { retrofit(get(), get()) }
    single { MockInterceptor() }
    single { moshi() }
}

private fun okHttpClient(
    authInterceptor: MockInterceptor
): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
    okHttpClient.connectTimeout(5, TimeUnit.MINUTES)
    okHttpClient.callTimeout(5, TimeUnit.MINUTES)
    okHttpClient.readTimeout(5, TimeUnit.MINUTES)
    okHttpClient.writeTimeout(5, TimeUnit.MINUTES)
    okHttpClient.addInterceptor(authInterceptor)
    return okHttpClient.build()
}

private fun moshi(): Moshi {
    return Moshi.Builder()
        .build()
}

fun retrofit(okHtpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHtpClient)
        .build()
}