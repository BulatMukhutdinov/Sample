package ru.bulat.mukhutdinov.sample.infrastructure.common.db

import androidx.room.TypeConverter
import ru.bulat.mukhutdinov.sample.post.model.PostType

class Converters {

    @TypeConverter
    fun postTypeToString(value: PostType) = value.serializedValue

    @TypeConverter
    fun stringToPostType(postTypeSerializedValue: String) =
        PostType.values().firstOrNull { it.serializedValue == postTypeSerializedValue } ?: PostType.UNKNOWN
}