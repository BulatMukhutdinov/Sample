package ru.bulat.mukhutdinov.sample.infrastructure.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.bulat.mukhutdinov.sample.diary.db.NoteDao
import ru.bulat.mukhutdinov.sample.diary.db.NoteEntity
import ru.bulat.mukhutdinov.sample.post.db.PostDao
import ru.bulat.mukhutdinov.sample.post.db.PostEntity
import ru.bulat.mukhutdinov.sample.user.db.UserDao
import ru.bulat.mukhutdinov.sample.user.db.UserEntity

@Database(
    entities = [
        UserEntity::class,
        PostEntity::class,
        NoteEntity::class
    ],
    version = 9,
    exportSchema = true)
@TypeConverters(Converters::class)
abstract class SampleDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun postDao(): PostDao

    abstract fun noteDao(): NoteDao
}