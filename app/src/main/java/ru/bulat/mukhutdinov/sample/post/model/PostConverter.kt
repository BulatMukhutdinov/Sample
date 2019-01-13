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

    fun toDatabase(source: Post) =
        PostEntity(
            id = source.id,
            date = source.date,
            body = source.body,
            tags = source.tags.joinToString(TAGS_SEPARATOR),
            title = source.title
        )

    fun fromDatabase(source: PostEntity) =
        Post(
            id = source.id,
            date = source.date,
            body = source.body ?: "",
            tags = source.tags.split(TAGS_SEPARATOR).map { it.trim() },
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
                    tags = textPost.tags.joinToString(TAGS_SEPARATOR),
                    title = textPost.title
                )
            }
            ANSWER -> {
                val answerPost = source as AnswerPost
                PostEntity(
                    id = answerPost.id,
                    date = answerPost.dateGMT,
                    tags = answerPost.tags.joinToString(TAGS_SEPARATOR)
                )
            }
            AUDIO -> {
                val audioPost = source as AudioPost
                PostEntity(
                    id = audioPost.id,
                    date = audioPost.dateGMT,
                    tags = audioPost.tags.joinToString(TAGS_SEPARATOR)
                )
            }
            CHAT -> {
                val chatPost = source as ChatPost
                PostEntity(
                    id = chatPost.id,
                    date = chatPost.dateGMT,
                    body = chatPost.body,
                    tags = chatPost.tags.joinToString(TAGS_SEPARATOR),
                    title = chatPost.title
                )
            }
            LINK -> {
                val linkPost = source as LinkPost
                PostEntity(
                    id = linkPost.id,
                    date = linkPost.dateGMT,
                    tags = linkPost.tags.joinToString(TAGS_SEPARATOR),
                    title = linkPost.title
                )
            }
            PHOTO -> {
                val photoPost = source as PhotoPost
                PostEntity(
                    id = photoPost.id,
                    date = photoPost.dateGMT,
                    tags = photoPost.tags.joinToString(TAGS_SEPARATOR)
                )
            }
            POSTCARD -> {
                val postcardPost = source as PostcardPost
                PostEntity(
                    id = postcardPost.id,
                    date = postcardPost.dateGMT,
                    body = postcardPost.body,
                    tags = postcardPost.tags.joinToString(TAGS_SEPARATOR)
                )
            }
            QUOTE -> {
                val quotePost = source as QuotePost
                PostEntity(
                    id = quotePost.id,
                    date = quotePost.dateGMT,
                    tags = quotePost.tags.joinToString(TAGS_SEPARATOR)
                )
            }
            VIDEO -> {
                val videoPost = source as VideoPost
                PostEntity(
                    id = videoPost.id,
                    date = videoPost.dateGMT,
                    tags = videoPost.tags.joinToString(TAGS_SEPARATOR)
                )
            }
            else -> throw UnsupportedOperationException("${source.javaClass.simpleName} type not supported yet")
        }

    fun toDatabase(source: List<PostDto>) =
        mutableListOf<PostEntity>().also {
            source.forEach { post -> it.add(fromNetwork(post)) }
        } as List<PostEntity>
}