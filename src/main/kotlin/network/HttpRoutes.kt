package org.example.network

object HttpRoutes {

    private const val BASE_URL = "http://localhost:3000/"

    val getList = BASE_URL + "anime"

    val postAnime = BASE_URL + "anime"
}