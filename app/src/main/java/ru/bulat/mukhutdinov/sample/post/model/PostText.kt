package ru.bulat.mukhutdinov.sample.post.model

import android.text.Html
import android.text.Spanned
import java.text.SimpleDateFormat
import java.util.Locale

class PostText(
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

    val formattedBody: Spanned = Html.fromHtml(body, Html.FROM_HTML_MODE_COMPACT)

    val formattedDate: String = formatDate()

    private fun formatDate(): String {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = parser.parse(date)

        val formatter = SimpleDateFormat("dd.MM, HH:mm", Locale.getDefault())

        return formatter.format(date)
    }
}