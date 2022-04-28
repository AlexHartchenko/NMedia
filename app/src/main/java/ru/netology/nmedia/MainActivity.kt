package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.post.Post
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась" +
                    " с интенсивов по онлайн-маркетингу. Затем появились курсы по " +
                    "дизайну, разработке, аналитике и управлению. Мы растём сами и " +
                    "помогаем расти студентам: от новичков до уверенных профессионалов." +
                    " Но самое важное остаётся с нами: мы верим, что в каждом уже есть" +
                    " сила, которая заставляет хотеть больше, целиться выше, бежать быстрее." +
                    " Наша миссия — помочь встать на путь роста и начать цепочку перемен" +
                    " → http://netolo.gy/fyb",
            published = "28 апреля 2022г. ",
            likes = 999,
            shared = 96,
            looksCount = 990,
            likedByMe = false
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe) {
                like.setImageResource(R.drawable.ic_like_24)
            }
            likes.text = format(post.likes)
            shared.text = format(post.shared)
            viewCount.text = format(post.looksCount)

            like.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likes.text = format(post.likes)
            }

            shares.setOnClickListener {
                shared.text = format(post.shared++)
            }

            views.setOnClickListener {
                viewCount.text = format(post.looksCount++)
            }

        }
    }

    private fun format(number: Int): String {
        val suffix = arrayOf("k", "m", "b", "t")
        var size = if (number != 0) log10(number.toDouble())
            .toInt() else 0
        if (size >= 3) {
            while (size % 3 != 0) {
                size -= 1
            }
        }
        val notation = 10.0.pow(size.toDouble())
        return if (size >= 3) (((number / notation * 100).roundToInt() / 100.0).toString() + suffix[size / 3 - 1]) else number.toString() + ""
    }



}



