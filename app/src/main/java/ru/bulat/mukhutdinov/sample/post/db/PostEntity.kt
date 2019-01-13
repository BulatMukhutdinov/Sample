package ru.bulat.mukhutdinov.sample.post.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.bulat.mukhutdinov.sample.post.db.PostEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class PostEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    var id: Long,
    @ColumnInfo(name = COLUMN_BODY)
    var body: String? = null,
    @ColumnInfo(name = COLUMN_TITLE)
    var title: String? = null,
    @ColumnInfo(name = COLUMN_DATE)
    var date: String,
    @ColumnInfo(name = COLUMN_TAGS)
    var tags: String
) {
    companion object {
        const val TABLE_NAME = "post"
        const val COLUMN_ID = "id"
        const val COLUMN_BODY = "body"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DATE = "date"
        const val COLUMN_TAGS = "tags"
    }
}