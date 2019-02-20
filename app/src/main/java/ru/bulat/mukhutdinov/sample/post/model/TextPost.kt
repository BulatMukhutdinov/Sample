package ru.bulat.mukhutdinov.sample.post.model

class TextPost(
        id: Long,
        avatar: String,
        date: String,
        tags: List<String>,
        blogName: String,
        isLiked: Boolean,
        val body: String,
        val title: String
) : Post(
        id = id,
        avatar = avatar,
        date = date,
        tags = tags,
        blogName = blogName,
        isLiked = isLiked
) {

    val formattedBody: String = body.replace("\n\n", "\n")
}