package ru.netology.nmedia.data.impl

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.floor
import kotlin.math.pow

internal class PostsAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val onSharedClicked: (Post) -> Unit
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)

        return ViewHolder(
            binding,
            onLikeClicked,
            onSharedClicked
        )
    }

    class ViewHolder(
        private val binding: PostBinding,
        onLikeClicked: (Post) -> Unit,
        onSharedClicked: (Post) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.like.setOnClickListener { onLikeClicked(post) }
            binding.shares.setOnClickListener { onSharedClicked(post) }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                likes.text = getFormattedNumber(post.likes)
                shared.text = getFormattedNumber(post.shared)
                viewCount.text = getFormattedNumber(post.viewCount)
                like.setImageResource(getButtonLikesIconResId(post.likedByMe))
            }
        }

        @DrawableRes
        private fun getButtonLikesIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_liked_24 else R.drawable.ic_like_24

        private fun getFormattedNumber(number: Int): String {
            return when (number) {
                0 -> ""
                in 1..999 -> String.format(
                    itemView.context.getString(R.string.numberOnes),
                    number.toFloat()
                )
                in 1_000..1_099 -> String.format(
                    itemView.context.getString(R.string.numberThousands),
                    getTruncatedNumber(number, 2, 1)
                )
                in 1_100..9_999 -> String.format(
                    itemView.context.getString(R.string.numberThousandsAndHundreds),
                    getTruncatedNumber(number, 2, 1)
                )
                in 10_000..999_999 -> String.format(
                    itemView.context.getString(R.string.numberThousands),
                    getTruncatedNumber(number, 2, 1)
                )
                in 1_000_000..1_099_000 -> String.format(
                    itemView.context.getString(R.string.numberMillions),
                    getTruncatedNumber(number, 6, 1)
                )
                in 1_100_000..9_999_999 -> String.format(
                    itemView.context.getString(R.string.numberMillionsAndThousands),
                    getTruncatedNumber(number, 6, 1)
                )
                else -> String.format(
                    itemView.context.getString(R.string.numberMillions),
                    getTruncatedNumber(number, 6, 1)
                )
            }
        }

        private fun getTruncatedNumber(
            number: Int,
            divideDigits: Int,
            truncateDigits: Int
        ): Double {
            return floor(number.toDouble() / 10F.pow(divideDigits) / 10F.pow(truncateDigits))
        }
    }

        private object DiffCallback : DiffUtil.ItemCallback<Post>() {

            override fun areItemsTheSame(oldItem: Post, newItem: Post) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Post, newItem: Post) =
                oldItem == newItem

        }

    }