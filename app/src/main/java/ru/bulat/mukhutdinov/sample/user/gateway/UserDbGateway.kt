package ru.bulat.mukhutdinov.sample.user.gateway

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.bulat.mukhutdinov.sample.infrastructure.exception.mapLocalExceptions
import ru.bulat.mukhutdinov.sample.user.db.UserDao
import ru.bulat.mukhutdinov.sample.user.model.User
import ru.bulat.mukhutdinov.sample.user.model.UserConverter

class UserDbGateway(private val userDao: UserDao) : UserLocalGateway {

    override fun findById(id: Long): Maybe<User> =
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
            .mapLocalExceptions()
}