package ru.netology.nerecipe.db

import ru.netology.nerecipe.dto.Post

internal fun PostEntity.toModel() = Post(
    id = id,
    author = author,
    content = content,
    published = published,
    likes = likes,
    likedByMe = likedByMe,
    reposts = reposts,
    views = views,
    videoURL = videoURL,
    )

internal fun Post.toEntity() = PostEntity(
    id = id,
    author = author,
    content = content,
    published = published,
    likes = likes,
    likedByMe = likedByMe,
    reposts = reposts,
    views = views,
    videoURL = videoURL,
)