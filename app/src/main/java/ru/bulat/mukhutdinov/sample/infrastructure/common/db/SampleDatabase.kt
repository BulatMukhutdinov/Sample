package ru.bulat.mukhutdinov.sample.infrastructure.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.bulat.mukhutdinov.sample.post.db.PostDao
import ru.bulat.mukhutdinov.sample.post.db.PostEntity
import ru.bulat.mukhutdinov.sample.user.db.UserDao
import ru.bulat.mukhutdinov.sample.user.db.UserEntity

@Database(
    entities = [
        UserEntity::class,
        PostEntity::class
    ],
    version = 5,
    exportSchema = true)
abstract class SampleDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun postDao(): PostDao
}