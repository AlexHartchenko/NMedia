package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post
import java.util.*


class InMemoryPostRepository : PostRepository {

    override val data = MutableLiveData(
        Post(
            id = 0L,
            likes = 999,
            shared = 970,
            viewCount = 960,
            published = ""
        )
    )

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

    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }

        val modifiedPost = currentPost.copy(
            likedByMe = !currentPost.likedByMe,
            likes = currentPost.likes + if (!currentPost.likedByMe) 1 else -1
        )
        data.value = modifiedPost
    }

    override fun repost() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val modifiedPost = currentPost.copy(
            shared = currentPost.shared + 10
        )
        data.value = modifiedPost
    }

    override fun views() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val modifiedPost = currentPost.copy(
            viewCount = currentPost.viewCount + 10
        )
        data.value = modifiedPost
    }

}