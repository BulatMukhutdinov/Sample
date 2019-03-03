package ru.bulat.mukhutdinov.sample.diary.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface NoteDao {

    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME} ORDER BY ${NoteEntity.COLUMN_TIMESTAMP}")
    fun getAll(): Flowable<List<NoteEntity>> // since there is no out-of-box solution to observe db using coroutines yes we will use rx flowable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: NoteEntity)
}