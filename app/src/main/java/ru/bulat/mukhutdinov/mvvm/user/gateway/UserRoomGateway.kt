package ru.bulat.mukhutdinov.mvvm.user.gateway

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.reactivex.Completable
import ru.bulat.mukhutdinov.mvvm.user.db.UserDao
import ru.bulat.mukhutdinov.mvvm.user.model.User
import ru.bulat.mukhutdinov.mvvm.user.model.UserConverter

class UserRoomGateway(private val userDao: UserDao) : UserLocalGateway {

    override fun findById(id: String): LiveData<User> =
        Transformations.map(userDao.findById(id)) { user ->
            UserConverter.fromDatabase(user)
        }

    override fun getAll(): LiveData<List<User>> =
        Transformations.map(userDao.getAll()) { userEntityList ->
            userEntityList.map { UserConverter.fromDatabase(it) }
        }

    override fun update(user: User): Completable =
        Completable.fromCallable { userDao.update(UserConverter.toDatabase(user)) }
}