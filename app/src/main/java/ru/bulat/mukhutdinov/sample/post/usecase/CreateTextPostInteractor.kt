package ru.bulat.mukhutdinov.sample.post.usecase

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import ru.bulat.mukhutdinov.sample.post.gateway.PostGateway
import ru.bulat.mukhutdinov.sample.post.model.TextPost
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class CreateTextPostInteractor(private val postGateway: PostGateway) : CreateTextPostUseCase {

    override fun execute(title: String, body: String, tags: List<String>): Completable =
            postGateway.currentAvatar()
                    .flatMapCompletable { avatar ->
                        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.getDefault())
                        formatter.timeZone = TimeZone.getTimeZone("GMT")
                        val date = formatter.format(Date())

                        val post = TextPost(
                                id = -1,
                                avatar = avatar,
                                blogName = postGateway.currentBlogName,
                                body = body,
                                date = date,
                                isLiked = false,
                                tags = tags,
                                title = title
                        )

                        postGateway.createTextPostRemote(post)
                                .subscribeOn(Schedulers.io())
                                .subscribe(
                                        { Timber.d("New post is Successfully created") },
                                        {
                                            // for now for consistency delete post locally if failed to create remotely
                                            // in the future we should create a queue that would be responsible for publishing local posts to server
                                            postGateway
                                                    .delete(post)
                                                    .subscribe()
                                        }
                                )

                        postGateway.createTextPostLocal(post)
                    }
}