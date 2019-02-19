package ru.bulat.mukhutdinov.sample.auth.gateway

import io.reactivex.Single

interface AuthApi {

    fun authenticate(name: String, passwordHash: String): Single<String>

    companion object {
        const val DEFAULT_AUTH_TOKEN_TYPE = "default_type"
    }
}