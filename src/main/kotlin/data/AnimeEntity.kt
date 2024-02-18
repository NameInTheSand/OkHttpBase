package org.example.data

import com.google.gson.annotations.SerializedName

data class AnimeEntity(
    val id:String,
    val name:String,
    @SerializedName("created_at")
    val creationTime:Long,
    val review:String,
    @SerializedName("main_genre")
    val genre:String,
    val year:Int,
    val rating:Int,
    @SerializedName("poster_link")
    val posterLink:String
)

