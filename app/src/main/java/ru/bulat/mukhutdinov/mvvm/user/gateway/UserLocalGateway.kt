package ru.bulat.mukhutdinov.mvvm.user.gateway

import androidx.lifecycle.LiveData
import io.reactivex.Completable
import ru.bulat.mukhutdinov.mvvm.user.model.User

interface UserLocalGateway {

    fun getAll(): LiveData<List<User>>

    fun findById(id: String): LiveData<User>

    fun update(user: User): Completable
}