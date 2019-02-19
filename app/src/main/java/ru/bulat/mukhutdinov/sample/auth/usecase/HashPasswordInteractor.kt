package ru.bulat.mukhutdinov.sample.auth.usecase

import io.reactivex.Single
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class HashPasswordInteractor : HashPasswordUseCase {

    override fun execute(password: String): Single<String> =
            Single.fromCallable {
                val random = SecureRandom()
                val salt = ByteArray(16)
                random.nextBytes(salt)
                val spec = PBEKeySpec(password.toCharArray(), salt, 65536, 128)
                val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                val hash = factory.generateSecret(spec).encoded
                hash.toString()
            }
}