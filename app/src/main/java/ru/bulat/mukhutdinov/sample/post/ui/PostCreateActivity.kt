package ru.bulat.mukhutdinov.sample.post.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.post.model.PostType

class PostCreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_create_activity)

        val type = intent.getSerializableExtra(POST_TYPE) as PostType
        createPost(type)

        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun createPost(type: PostType) {
    }

    companion object {
        const val POST_TYPE = "post_type"
    }
}