package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post


class InMemoryPostRepository : PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data = MutableLiveData(
        List(10) { index ->
            Post(
                id = index + 1L,
                author = "Alex",
                content = "text of the Post #$index",
                likes = 999,
                shared = 970,
                viewCount = 960,
                published = ""
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
                shared = it.shared + 10
            )
        }

    }
}

//    init {
//        GlobalScope.launch {
//            while (true) {
//                delay(1000)
//                val currentPost = checkNotNull(data.value) {
//                    "Data value should not be null"
//                }
//
//                val newPost = currentPost.copy(
//                    published = Date().toString()
//                )
//                data.postValue(newPost)
//
//            }
//        }
//    }