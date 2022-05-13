package ru.netology.nmedia.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent
import java.text.SimpleDateFormat
import java.util.*

class SinglePostViewModel(
    application: Application
) : AndroidViewModel(application),
    PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository

    val data by repository::data

    val currentPost = MutableLiveData<Post?>(null)

    val sharePostContent = SingleLiveEvent<String>()
    val navigateToEditContentScreenEvent = SingleLiveEvent<String>()
    val playVideoURL = SingleLiveEvent<String>()
    val removePost = SingleLiveEvent<Unit>()

    fun getPostById(postId: Long) : Post? =
        repository.getById(postId)

    @SuppressLint("SimpleDateFormat")
    fun onButtonSaveClicked(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Тестировщик ))",
            content = content,
            published = SimpleDateFormat("dd.MM.yyyy hh:mm").format(Date()),

            )
        repository.save(post)
        currentPost.value = null
    }

    override fun onButtonRepostsClicked(post: Post) {
        sharePostContent.value = post.content
        repository.repost(post.id)
    }

    override fun onButtonPlayVideoClicked(post: Post) {
        playVideoURL.value = post.videoURL
    }

    override fun onContentClicked(post: Post) {

    }

    override fun onButtonLikesClicked(post: Post) =
        repository.like(post.id)

    override fun onButtonRemoveClicked(post: Post) {
        repository.remove(post.id)
        removePost.value = Unit
    }

    override fun onButtonEditClicked(post: Post) {
        currentPost.value = post
        navigateToEditContentScreenEvent.value = post.content
    }

}