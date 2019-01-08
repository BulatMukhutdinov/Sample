package ru.bulat.mukhutdinov.sample.post.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Maybe

@Dao
interface PostDao {

    @Query("SELECT * FROM ${PostEntity.TABLE_NAME}")
    fun getAll(): DataSource.Factory<Int, PostEntity>

    @Query("SELECT * FROM ${PostEntity.TABLE_NAME} WHERE ${PostEntity.COLUMN_ID} = :id")
    fun findById(id: String): Maybe<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(user: List<PostEntity>)

    @Update
    fun update(user: PostEntity)

    @Query("DELETE FROM ${PostEntity.TABLE_NAME}")
    fun clear()
}