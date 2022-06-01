package ru.netology.nerecipe.adapter

import ru.netology.nerecipe.dto.Post

interface PostInteractionListener {

//    fun onAddClicked()
    fun onButtonLikesClicked(post: Post)
    fun onButtonRepostsClicked(post: Post)
    fun onButtonRemoveClicked(post: Post)
    fun onButtonEditClicked(post: Post)
    fun onButtonPlayVideoClicked(post: Post)

}