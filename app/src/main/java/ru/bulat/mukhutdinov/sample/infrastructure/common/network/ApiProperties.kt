package ru.bulat.mukhutdinov.sample.infrastructure.common.network

data class ApiProperties(
    val consumerKey: String,
    val consumerSecret: String,
    val token: String,
    val tokenSecret: String,
    val apiKey: String
)