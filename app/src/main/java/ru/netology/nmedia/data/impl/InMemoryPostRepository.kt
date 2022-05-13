package ru.netology.nmedia.data.impl

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post
import java.text.SimpleDateFormat
import java.util.*

object InMemoryPostRepository : PostRepository {

    private const val GENERATED_POSTS_AMOUNT = 15

    private var nextId = GENERATED_POSTS_AMOUNT.toLong()

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    @SuppressLint("SimpleDateFormat")
    override val data = MutableLiveData(
        List(GENERATED_POSTS_AMOUNT) { index ->
            Post(
                id = index + 1L,
                author = "Alex",
                content = "Здесь могла бы быть Ваша реклама! тел№ $index",
                published = SimpleDateFormat("dd.MM.yyyy hh:mm").format(Date()),
                likes = (0..999).random(),
                reposts = (0..99).random(),
                views = (3..1199).random(),
                videoURL = if (index % 4 == 0) "https://www.youtube.com/watch?v=gJt946CyJO0" else "",
            )
        }
    )

    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(
                likedByMe = !it.likedByMe,
                likes = it.likes + if (!it.likedByMe) 1 else -1
            )
        }
    }

    override fun repost(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(
                reposts = it.reposts + 1
            )
        }
    }

    override fun remove(postId: Long) {
        data.value = posts.filter { it.id != postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        data.value = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    override fun getById(postId: Long): Post? {
        return posts.find { it.id == postId }
    }

}