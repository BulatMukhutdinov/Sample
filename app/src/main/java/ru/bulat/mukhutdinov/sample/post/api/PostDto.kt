package ru.bulat.mukhutdinov.sample.post.api

import com.google.gson.annotations.SerializedName

class PostDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatar: String,
    @SerializedName("url")
    val url: String
)