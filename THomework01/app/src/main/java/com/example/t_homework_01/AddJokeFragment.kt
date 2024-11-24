package com.example.t_homework_01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider

class AddJokeFragment : Fragment() {

    private val addJokeViewModel: AddJokeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AddJokeScreen(
                    viewModel = addJokeViewModel,
                    onJokeAdded = { joke ->
                        parentFragmentManager.popBackStack()
                        (activity as? MainActivity)?.let {
                            val jokeViewModel = ViewModelProvider(it)[JokeViewModel::class.java]
                            jokeViewModel.addJoke(joke)
                        }
                    }
                )
            }
        }
    }

    companion object {
        fun newInstance(): AddJokeFragment {
            return AddJokeFragment()
        }
    }
}