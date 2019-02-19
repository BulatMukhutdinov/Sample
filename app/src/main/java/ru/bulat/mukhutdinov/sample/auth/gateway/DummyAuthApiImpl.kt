package ru.bulat.mukhutdinov.sample.auth.gateway

import io.reactivex.Single
import java.security.SecureRandom

class DummyAuthApiImpl : AuthApi {

    override fun authenticate(name: String, passwordHash: String): Single<String> =
            Single.fromCallable {
                val random = SecureRandom()
                val bytes = ByteArray(20)
                random.nextBytes(bytes)
                bytes.toString()
            }
}