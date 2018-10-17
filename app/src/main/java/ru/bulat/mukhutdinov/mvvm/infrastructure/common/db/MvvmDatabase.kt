package ru.bulat.mukhutdinov.mvvm.infrastructure.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.bulat.mukhutdinov.mvvm.user.db.UserDao
import ru.bulat.mukhutdinov.mvvm.user.db.UserEntity


@Database(
    entities = [
        UserEntity::class
    ],
    version = 1,
    exportSchema = true)
abstract class MvvmDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}