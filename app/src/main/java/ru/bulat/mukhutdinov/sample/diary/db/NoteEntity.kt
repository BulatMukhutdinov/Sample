package ru.bulat.mukhutdinov.sample.diary.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.bulat.mukhutdinov.sample.diary.db.NoteEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long,
    @ColumnInfo(name = COLUMN_TEXT)
    var text: String,
    @ColumnInfo(name = COLUMN_TIMESTAMP)
    var timestamp: Long
) {
    companion object {
        const val TABLE_NAME = "note"
        const val COLUMN_ID = "id"
        const val COLUMN_TEXT = "text"
        const val COLUMN_TIMESTAMP = "timestamp"
    }
}