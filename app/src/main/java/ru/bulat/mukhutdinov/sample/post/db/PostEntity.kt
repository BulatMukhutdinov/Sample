package ru.bulat.mukhutdinov.sample.post.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.bulat.mukhutdinov.sample.post.db.PostEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class PostEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    var id: Int,
    @ColumnInfo(name = COLUMN_LOGIN)
    var login: String,
    @ColumnInfo(name = COLUMN_AVATAR)
    var avatar: String,
    @ColumnInfo(name = COLUMN_URL)
    var url: String
) {
    companion object {
        const val TABLE_NAME = "post"
        const val COLUMN_ID = "id"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_AVATAR = "avatar"
        const val COLUMN_URL = "url"
    }
}