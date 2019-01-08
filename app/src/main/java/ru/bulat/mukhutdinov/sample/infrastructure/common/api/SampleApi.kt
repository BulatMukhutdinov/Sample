package ru.bulat.mukhutdinov.sample.infrastructure.common.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.bulat.mukhutdinov.sample.post.api.PostDto

interface SampleApi {

    @GET("/users")
    fun get(@Query("per_page") pageSize: Int): Call<List<PostDto>>

    @GET("/users")
    fun getSince(@Query("since") since: Int,
                 @Query("per_page") pageSize: Int): Call<List<PostDto>>
}