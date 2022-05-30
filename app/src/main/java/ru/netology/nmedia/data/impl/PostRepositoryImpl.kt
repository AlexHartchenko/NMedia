package ru.netology.nmedia.data.impl

import androidx.lifecycle.map
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostsDao
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel
import ru.netology.nmedia.dto.Post

class PostRepositoryImpl(
    private val dao: PostsDao
) : PostRepository {

   override val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun save(post: Post) {
        dao.save(post.toEntity())
    }

    override fun like(postId: Long) {
        dao.likeById(postId)
    }

    override fun repost(postId: Long) {
        dao.repost(postId)
    }

    override fun remove(postId: Long) {
        dao.removeById(postId)

    }
}