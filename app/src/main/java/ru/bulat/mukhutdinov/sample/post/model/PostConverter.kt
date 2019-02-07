package ru.bulat.mukhutdinov.sample.post.model

import com.tumblr.jumblr.types.AnswerPost
import com.tumblr.jumblr.types.AudioPost
import com.tumblr.jumblr.types.ChatPost
import com.tumblr.jumblr.types.LinkPost
import com.tumblr.jumblr.types.PhotoPost
import com.tumblr.jumblr.types.Post.PostType.ANSWER
import com.tumblr.jumblr.types.Post.PostType.AUDIO
import com.tumblr.jumblr.types.Post.PostType.CHAT
import com.tumblr.jumblr.types.Post.PostType.LINK
import com.tumblr.jumblr.types.Post.PostType.PHOTO
import com.tumblr.jumblr.types.Post.PostType.POSTCARD
import com.tumblr.jumblr.types.Post.PostType.QUOTE
import com.tumblr.jumblr.types.Post.PostType.TEXT
import com.tumblr.jumblr.types.Post.PostType.VIDEO
import com.tumblr.jumblr.types.PostcardPost
import com.tumblr.jumblr.types.QuotePost
import com.tumblr.jumblr.types.TextPost
import com.tumblr.jumblr.types.VideoPost
import ru.bulat.mukhutdinov.sample.post.db.PostEntity

typealias PostDto = com.tumblr.jumblr.types.Post

object PostConverter {

    private const val TAGS_SEPARATOR = ","

    fun toDatabase(source: PostText) =
        PostEntity(
            id = source.id,
            date = source.date,
            body = source.body,
            blogName = source.blogName,
            tags = source.tags.joinToString(TAGS_SEPARATOR) { it.substring(1) },
            title = source.title,
            type = PostType.TEXT
        )

    fun fromDatabase(source: PostEntity): Post =
        when (source.type) {
            PostType.TEXT -> createTextPost(source)
            PostType.IMAGE -> TODO()
            PostType.QUOTE -> TODO()
            PostType.LINK -> TODO()
            PostType.CHAT -> TODO()
            PostType.AUDIO -> TODO()
            PostType.VIDEO -> TODO()
            PostType.ANSWER -> TODO()
            PostType.POSTCARD -> TODO()
            PostType.UNKNOWN -> TODO()
        }

    private fun createTextPost(source: PostEntity) =
        PostText(
            id = source.id,
            date = source.date,
            blogName = source.blogName,
            body = source.body ?: "",
            avatar = source.avatar ?: "",
            tags = source.tags.split(TAGS_SEPARATOR).map { it.trim() }.filter { it.isNotEmpty() }.map { "#$it" },
            title = source.title ?: ""
        )

    fun fromNetwork(source: PostDto) =
        when (source.type) {
            TEXT -> {
                val textPost = source as TextPost
                PostEntity(
                    id = textPost.id,
                    date = textPost.dateGMT,
                    body = textPost.body,
                    blogName = source.blogName,
                    tags = textPost.tags.joinToString(TAGS_SEPARATOR),
                    title = textPost.title,
                    type = PostType.TEXT
                )
            }
            ANSWER -> {
                val answerPost = source as AnswerPost
                PostEntity(
                    id = answerPost.id,
                    date = answerPost.dateGMT,
                    blogName = answerPost.blogName,
                    tags = answerPost.tags.joinToString(TAGS_SEPARATOR),
                    type = PostType.ANSWER
                )
            }
            AUDIO -> {
                val audioPost = source as AudioPost
                PostEntity(
                    id = audioPost.id,
                    date = audioPost.dateGMT,
                    blogName = audioPost.blogName,
                    tags = audioPost.tags.joinToString(TAGS_SEPARATOR),
                    type = PostType.AUDIO
                )
            }
            CHAT -> {
                val chatPost = source as ChatPost
                PostEntity(
                    id = chatPost.id,
                    date = chatPost.dateGMT,
                    body = chatPost.body,
                    blogName = chatPost.blogName,
                    tags = chatPost.tags.joinToString(TAGS_SEPARATOR),
                    title = chatPost.title,
                    type = PostType.CHAT
                )
            }
            LINK -> {
                val linkPost = source as LinkPost
                PostEntity(
                    id = linkPost.id,
                    date = linkPost.dateGMT,
                    blogName = linkPost.blogName,
                    tags = linkPost.tags.joinToString(TAGS_SEPARATOR),
                    title = linkPost.title,
                    type = PostType.LINK
                )
            }
            PHOTO -> {
                val photoPost = source as PhotoPost
                PostEntity(
                    id = photoPost.id,
                    date = photoPost.dateGMT,
                    blogName = photoPost.blogName,
                    tags = photoPost.tags.joinToString(TAGS_SEPARATOR),
                    type = PostType.IMAGE
                )
            }
            POSTCARD -> {
                val postcardPost = source as PostcardPost
                PostEntity(
                    id = postcardPost.id,
                    date = postcardPost.dateGMT,
                    body = postcardPost.body,
                    blogName = postcardPost.blogName,
                    tags = postcardPost.tags.joinToString(TAGS_SEPARATOR),
                    type = PostType.POSTCARD
                )
            }
            QUOTE -> {
                val quotePost = source as QuotePost
                PostEntity(
                    id = quotePost.id,
                    date = quotePost.dateGMT,
                    blogName = quotePost.blogName,
                    tags = quotePost.tags.joinToString(TAGS_SEPARATOR),
                    type = PostType.QUOTE
                )
            }
            VIDEO -> {
                val videoPost = source as VideoPost
                PostEntity(
                    id = videoPost.id,
                    date = videoPost.dateGMT,
                    blogName = videoPost.blogName,
                    tags = videoPost.tags.joinToString(TAGS_SEPARATOR),
                    type = PostType.VIDEO
                )
            }
            else -> throw UnsupportedOperationException("${source.type} type not supported yet")
        }
}