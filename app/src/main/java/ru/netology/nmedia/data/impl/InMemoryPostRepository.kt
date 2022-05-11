package ru.netology.nmedia.data.impl

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


    override val data = MutableLiveData(
        List(GENERATED_POSTS_AMOUNT) { index ->
            Post(
                id = index + 1L,
                author = "Alex",
                content = "text of the Post #$index",
                published = SimpleDateFormat("dd.MM.yyyy hh:mm").format(Date()),
                likes = (0..999).random(),
                reposts = (0..1999).random(),
                views = (10..9099).random(),
                videoURL = if (index % 3 == 0) "https://www.youtube.com/watch?v=gJt946CyJO0" else "",
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
                reposts = it.reposts + 10
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
