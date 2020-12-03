package com.example.robustatask

import arrow.core.Either
import arrow.core.left
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safe(call: suspend () -> Either<AppFailure, T>): Either<AppFailure, T> {
    return try {
        return call()
    } catch (e: Throwable) {
        e.printStackTrace()
        e.toFailure().left()
    }
}

fun Throwable.toFailure(): AppFailure {
    return when (this) {
        is HttpException -> {
            when (code()) {
                in 400 until 500 -> ValidationErrors.Validation("Data not valid.")
                in 500 until 600 -> ApiErrors.ServerError("Server is busy. Please try again later.")
                else -> GeneralErrors.General("Something went wrong. Please try again later.")
            }
        }
        is IOException -> ApiErrors.NetworkConnection("Check your internet connection.")
        is JsonDataException -> ApiErrors.ServerError("Data does not match")
        else -> GeneralErrors.General("Something went wrong. Please try again later.")
    }
}

