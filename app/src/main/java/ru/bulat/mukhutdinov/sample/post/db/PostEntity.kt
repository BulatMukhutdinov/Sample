package ru.bulat.mukhutdinov.sample.post.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.bulat.mukhutdinov.sample.post.db.PostEntity.Companion.TABLE_NAME
import ru.bulat.mukhutdinov.sample.post.model.PostType

@Entity(tableName = TABLE_NAME)
class PostEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    var id: Long,
    @ColumnInfo(name = COLUMN_BODY)
    var body: String? = null,
    @ColumnInfo(name = COLUMN_BLOG_NAME)
    var blogName: String? = null,
    @ColumnInfo(name = COLUMN_AVATAR)
    var avatar: String? = null,
    @ColumnInfo(name = COLUMN_TITLE)
    var title: String? = null,
    @ColumnInfo(name = COLUMN_DATE)
    var date: String? = null,
    @ColumnInfo(name = COLUMN_TAGS)
    var tags: String = "",
    @ColumnInfo(name = COLUMN_TYPE)
    var type: PostType = PostType.UNKNOWN,
    @ColumnInfo(name = COLUMN_AUTHOR)
    var author: String? = null,
    @ColumnInfo(name = COLUMN_IS_LIKED)
    var isLiked: Boolean? = null,
    @ColumnInfo(name = COLUMN_REBLOG_KEY)
    var reblogKey: String? = null,
    @ColumnInfo(name = COLUMN_POST_URL)
    var postUrl: String? = null,
    @ColumnInfo(name = COLUMN_SHORT_URL)
    var shortUrl: String? = null,
    @ColumnInfo(name = COLUMN_TIMESTAMP)
    var timestamp: Long? = null,
    @ColumnInfo(name = COLUMN_LIKED_TIMESTAMP)
    var likedTimestamp: Long? = null
) {
    companion object {
        const val TABLE_NAME = "post"
        const val COLUMN_ID = "id"
        const val COLUMN_BODY = "body"
        const val COLUMN_BLOG_NAME = "blog_name"
        const val COLUMN_AVATAR = "avatar"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DATE = "date"
        const val COLUMN_TAGS = "tags"
        const val COLUMN_TYPE = "type"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_IS_LIKED = "is_liked"
        const val COLUMN_POST_URL = "post_url"
        const val COLUMN_REBLOG_KEY = "reblog_key"
        const val COLUMN_SHORT_URL = "short_url"
        const val COLUMN_TIMESTAMP = "timestamp"
        const val COLUMN_LIKED_TIMESTAMP = "liked_timestamp"
    }
}