package ru.bulat.mukhutdinov.sample.post.model

import android.text.Html
import android.text.Spanned
import ru.bulat.mukhutdinov.sample.infrastructure.common.model.BaseModel
import java.text.SimpleDateFormat
import java.util.Locale

data class Post(
    override val id: Long,
    var body: String,
    var avatar: String,
    var title: String,
    var date: String,
    var tags: List<String>
) : BaseModel(id) {

    val formattedBody: Spanned = Html.fromHtml(body, Html.FROM_HTML_MODE_COMPACT)

    val formattedDate: String = formatDate()

    private fun formatDate(): String {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = parser.parse(date)

        val formatter = SimpleDateFormat("dd.MM, HH:mm", Locale.getDefault())

        return formatter.format(date)
    }
}