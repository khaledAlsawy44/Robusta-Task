package com.example.robustatask.di

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.util.*

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val uri = chain.request().url.toUri().toString()
        val searchKey = uri.substringAfterLast("/").substringBefore("-")
        val page = uri.substringAfterLast("-").toInt()
        val responseString = getResult(searchKey, page)

        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                responseString.toByteArray()
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
            .addHeader("content-type", "application/json")
            .build()

    }

    private fun getResult(key: String, page: Int): String {
        val responseObject = JSONObject()
        responseObject.put("Success", true)
        responseObject.put("Code", 200)

        var message = "message : "
        val resultList = JSONArray()
        products.forEachIndexed { index, s ->
            if (s.toLowerCase(Locale.getDefault())
                    .contains(key.toLowerCase(Locale.getDefault()))
            ) {
                val jsonObject = JSONObject()
                jsonObject.put("productId", index)
                jsonObject.put("productName", s)
                resultList.put(jsonObject)

                message += " $s"
                Timber.tag("fffffff").d(resultList.toString())
            }else{
                message += "$key - $s"
            }
        }
        responseObject.put("Message", message)

        responseObject.put("Data", resultList)

        return responseObject.toString()
    }

    private val products = listOf(
        "Cheese 1", "Cheese 2", "Cheese 3",
        "Milk 1", "Milk 2", "Milk 3", "Milk 4", "Milk 5",
    )


}