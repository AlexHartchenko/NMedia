package ru.netology.nerecipe.data

import androidx.lifecycle.LiveData
import ru.netology.nerecipe.dto.Post

interface PostRepository {

    val data: LiveData<List<Post>>

    fun like(postId: Long)
    fun repost(postId: Long)
    fun remove(postId: Long)
    fun save(post: Post)

    companion object {
        const val NEW_POST_ID = 0L
    }

}