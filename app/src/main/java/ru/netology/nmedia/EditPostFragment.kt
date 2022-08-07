package ru.netology.nmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentEditPostBinding
import ru.netology.nmedia.util.hideKeyboard

class EditPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentEditPostBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        binding.textNewPost.requestFocus()

        binding.textNewPost.setText(viewModel.editedPost.value?.content)

        binding.fabOk.setOnClickListener {

            if (binding.textNewPost.text.isNotBlank()) {

                viewModel.onSaveContent(binding.textNewPost.text.toString())
                requireView().hideKeyboard()
            }

            findNavController().navigateUp()

        }

        return binding.root

    }

}