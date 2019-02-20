package ru.bulat.mukhutdinov.sample.post.db

import androidx.paging.DataSource
import androidx.room.*
import io.reactivex.Maybe

@Dao
interface PostDao {

    @Query("SELECT * FROM ${PostEntity.TABLE_NAME} ORDER BY ${PostEntity.COLUMN_DATE} DESC")
    fun getAll(): DataSource.Factory<Int, PostEntity>

    @Query("SELECT * FROM ${PostEntity.TABLE_NAME} WHERE ${PostEntity.COLUMN_ID} = :id")
    fun findById(id: Long): Maybe<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<PostEntity>)

    @Update
    fun update(post: PostEntity)

    @Delete
    fun delete(post: PostEntity)

    @Query("DELETE FROM ${PostEntity.TABLE_NAME}")
    fun clear()

    @Query("UPDATE ${PostEntity.TABLE_NAME} SET ${PostEntity.COLUMN_ID} = :newId WHERE ${PostEntity.COLUMN_ID} = :oldId")
    fun updateId(oldId: Long, newId: Long)
}