package ru.bulat.mukhutdinov.sample.user.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.bulat.mukhutdinov.sample.user.db.UserEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    var id: Int,
    @ColumnInfo(name = COLUMN_NAME)
    var name: String,
    @ColumnInfo(name = COLUMN_ICON)
    var icon: String,
    @ColumnInfo(name = COLUMN_ICON_THUMBNAIL)
    var iconThumbnail: String,
    @ColumnInfo(name = COLUMN_DESCRIPTION)
    var description: String
) {
    companion object {
        const val TABLE_NAME = "user"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_ICON = "icon"
        const val COLUMN_ICON_THUMBNAIL = "iconThumbnail"
        const val COLUMN_DESCRIPTION = "description"
    }
}