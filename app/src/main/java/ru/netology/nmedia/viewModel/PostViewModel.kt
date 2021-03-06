package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository

import ru.netology.nmedia.data.impl.PostRepositoryImpl
import ru.netology.nmedia.db.AppDb

import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent
import java.text.SimpleDateFormat
import java.util.*

class PostViewModel(
    application: Application

) : AndroidViewModel(application), PostInteractionListener {

    private val repository: PostRepository = PostRepositoryImpl(
            dao = AppDb.getInstance(
                context = application
            ).postsDao
        )


    val data by repository::data

    val sharePostContent = SingleLiveEvent<String>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()
    val playVideoURL = SingleLiveEvent<String>()

    val currentPost = MutableLiveData<Post?>(null)

    fun onAddClicked() {
        navigateToPostContentScreenEvent.call()
    }

    fun onButtonSaveClicked(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Alex",
            content = content,
            published = SimpleDateFormat("dd.MM.yyyy hh:mm").format(Date()),
            )
        repository.save(post)
        currentPost.value = null
    }

    override fun onButtonLikesClicked(post: Post) =
        repository.like(post.id)

    override fun onButtonRepostsClicked(post: Post) {
        sharePostContent.value = post.content
        repository.repost(post.id)
    }

    override fun onButtonPlayVideoClicked(post: Post) {
        playVideoURL.value = post.videoURL
    }

    override fun onButtonRemoveClicked(post: Post) =
        repository.remove(post.id)

    override fun onButtonEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.content
    }


}