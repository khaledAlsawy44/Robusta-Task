package com.example.robustatask

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.squareup.moshi.JsonClass
import retrofit2.Response
import timber.log.Timber

@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    val Success: Boolean?,
    val Code: Int?,
    val Data: T?,
    val Message: String?
)

fun <T, R> ApiResponse<T>.mapResponseData(transform: (T) -> R?): Either<AppFailure, R> {
    Timber.tag("ssssssssss").d(this.toString())

    if (Success == false)
        return ApiErrors.ServerError(Message).left()

    if (Success == true && Data != null) return transform(Data)?.right()
        ?: return ApiErrors.ServerError().left()

    return ApiErrors.ServerError("Something went wrong. Please try again later.").left()
}