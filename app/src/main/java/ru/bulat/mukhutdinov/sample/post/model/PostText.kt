package ru.bulat.mukhutdinov.sample.post.model

import android.text.Html
import android.text.Spanned
import java.text.SimpleDateFormat
import java.util.Locale

data class PostText constructor(
    override val id: Long,
    override var avatar: String,
    override var title: String,
    override var blogName: String,
    var body: String,
    var date: String,
    var tags: List<String>
) : Post(id = id, avatar = avatar, title = title, blogName = blogName) {

    val formattedBody: Spanned = Html.fromHtml(body, Html.FROM_HTML_MODE_COMPACT)

    val formattedDate: String = formatDate()

    private fun formatDate(): String {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = parser.parse(date)

        val formatter = SimpleDateFormat("dd.MM, HH:mm", Locale.getDefault())

        return formatter.format(date)
    }
}