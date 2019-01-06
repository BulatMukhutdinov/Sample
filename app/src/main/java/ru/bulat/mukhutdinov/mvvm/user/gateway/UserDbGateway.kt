package ru.bulat.mukhutdinov.mvvm.user.gateway

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.bulat.mukhutdinov.mvvm.infrastructure.exception.mapLocalExceptions
import ru.bulat.mukhutdinov.mvvm.user.db.UserDao
import ru.bulat.mukhutdinov.mvvm.user.model.User
import ru.bulat.mukhutdinov.mvvm.user.model.UserConverter
import java.util.concurrent.TimeUnit

class UserDbGateway(private val userDao: UserDao) : UserLocalGateway {

    override fun findById(id: String): Maybe<User> =
        userDao.findById(id)
            .map { UserConverter.fromDatabase(it) }
            .mapLocalExceptions()

    override fun getAll(): Single<List<User>> =
        userDao.getAll()
            .flattenAsFlowable { it }
            .map { UserConverter.fromDatabase(it) }
            .toList()
            .mapLocalExceptions()

    override fun update(user: User): Completable =
        Completable.fromCallable { userDao.update(UserConverter.toDatabase(user)) }
            .delay(3, TimeUnit.SECONDS)
            .mapLocalExceptions()
}