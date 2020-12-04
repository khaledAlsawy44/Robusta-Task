package com.example.robustatask.di

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONArray
import org.json.JSONObject
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

        var testMessage = "Message : "
        var resultSize = 0
        val resultList = JSONArray()
        products.forEachIndexed { index, s ->
            if (s.toLowerCase(Locale.getDefault())
                    .contains(key.toLowerCase(Locale.getDefault()))
            ) {
                if (resultSize < page * 10) {
                    val jsonObject = JSONObject()
                    jsonObject.put("productId", index)
                    jsonObject.put("productName", s)
                    jsonObject.put("productDescription", descriptions[index])
                    resultList.put(jsonObject)
                    resultSize++
                    testMessage += resultSize
                } else {
                    return@forEachIndexed
                }
            }
        }

        responseObject.put("Message", testMessage)
        responseObject.put("Data", resultList)

        return responseObject.toString()
    }

    private val products = listOf(
        "Cheese 1",
        "Cheese 2",
        "Cheese 3",
        "Cheese 4",
        "Cheese 5",
        "Cheese 6",
        "Cheese 7",
        "Cheese 8",
        "Cheese 9",
        "Cheese 10",
        "Cheese 11",
        "Cheese 12",
        "Cheese 13",
        "Cheese 14",
        "Cheese 15",
        "Cheese 16",
        "Cheese 17",
        "Cheese 18",
        "Milk 1",
        "Milk 2",
        "Milk 3",
        "Milk 4",
        "Milk 5",
    )
    private val descriptions = listOf(
        "Cheese 1 description",
        "Cheese 2 description",
        "Cheese 3 description",
        "Cheese 4 description",
        "Cheese 5 description",
        "Cheese 6 description",
        "Cheese 7 description",
        "Cheese 8 description",
        "Cheese 9 description",
        "Cheese 10 description ",
        "Cheese 11 description",
        "Cheese 12 description",
        "Cheese 13 description",
        "Cheese 14 description",
        "Cheese 15 description",
        "Cheese 16 description",
        "Cheese 17 description",
        "Cheese 18 description",
        "Milk 1 description",
        "Milk 2 description",
        "Milk 3 description",
        "Milk 4 description",
        "Milk 5 description",
    )


}