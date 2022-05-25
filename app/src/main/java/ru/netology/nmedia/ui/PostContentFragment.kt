package ru.netology.nmedia.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.PostContentFragmentBinding

class PostContentFragment : Fragment() {

    private val  initialContent
    get() = requireArguments().getString(INITIAL_CONTENT_ARGS_KEY)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostContentFragmentBinding.inflate(
        layoutInflater, container, false).also { binding ->
        binding.edit.setText(initialContent)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding)
        }
    }.root

    private fun onOkButtonClicked(binding: PostContentFragmentBinding) {
        val text = binding.edit.text

        if (!text.isNullOrBlank()) {
            val resultBundle = Bundle(1)
            resultBundle.putString(RESULT_KEY, text.toString())
            setFragmentResult(REQUEST_KEY, resultBundle)
        }
        findNavController().popBackStack()
    }

    companion object {
        private const val INITIAL_CONTENT_ARGS_KEY = "initialContent"
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "postNewContent"

        fun create(initialContent: String?) = PostContentFragment().apply {
            arguments = createBundle(initialContent)
        }


        fun createBundle(initialContent: String?) = Bundle(1).apply {
            putString(INITIAL_CONTENT_ARGS_KEY, initialContent)
        }
    }
}