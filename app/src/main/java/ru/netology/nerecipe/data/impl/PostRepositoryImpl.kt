package ru.netology.nerecipe.data.impl

import androidx.lifecycle.map
import ru.netology.nerecipe.data.PostRepository
import ru.netology.nerecipe.db.PostsDao
import ru.netology.nerecipe.db.toEntity
import ru.netology.nerecipe.db.toModel
import ru.netology.nerecipe.dto.Post

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