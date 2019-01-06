package ru.bulat.mukhutdinov.mvvm.user.gateway

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.bulat.mukhutdinov.mvvm.user.model.User

interface UserLocalGateway {

    fun getAll(): Single<List<User>>

    fun findById(id: String): Maybe<User>

    fun update(user: User): Completable
}