package org.example

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.example.data.AnimeEntity
import org.example.network.HttpRoutes
import org.example.network.NetworkException
import org.example.network.PageNotFoundException

private val contentType = "application/json; charset=utf-8".toMediaType()

fun main() {
    val gson = Gson()
    val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val getRequest = Request.Builder()
        .get()
        .url(HttpRoutes.getList)
        .build()

    val getCall = okHttpClient.newCall(getRequest)

    val postBody = gson.toJson(
        AnimeEntity(
            id = "123",
            name = "Name",
            creationTime = System.currentTimeMillis(),
            review = "Review",
            genre = "Genre",
            year = 1999,
            rating = 7,
            posterLink = "Custom link"
        )
    ).toRequestBody(contentType)

    val postRequest = Request.Builder()
        .post(postBody)
        .url(HttpRoutes.postAnime)
        .build()

    val postCall = okHttpClient.newCall(postRequest)

    try {
        val request = postCall.execute()
        if (request.isSuccessful) {
            println(request.code)
        } else {
            if (request.code == 404) {
                throw PageNotFoundException()
            }
            throw IllegalStateException("Error")
        }
    } catch (e: Exception) {
        if (e is NetworkException) {
            println("Network message " + e.message)
        } else {
            println(e.message)
        }
    }

    try {
        val request = getCall.execute()
        if (request.isSuccessful) {
            println(request.code)
        } else {
            if (request.code == 404) {
                throw PageNotFoundException()
            }
            throw IllegalStateException("Error")
        }
    } catch (e: Exception) {
        if (e is NetworkException) {
            println("Network message " + e.message)
        } else {
            println(e.message)
        }
    }

}