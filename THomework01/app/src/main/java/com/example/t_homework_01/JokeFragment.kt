package com.example.t_homework_01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class JokeFragment : Fragment() {

    private val jokeDetailViewModel: JokeDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val jokeId = arguments?.getString("jokeId") ?: ""
                val joke = jokeDetailViewModel.joke.observeAsState().value

                jokeDetailViewModel.loadJokeById(jokeId, (activity as MainActivity).jokeViewModel)

                if (joke != null) {
                    JokeDetails(
                        category = joke.category,
                        question = joke.question,
                        answer = joke.answer,
                        source = if (joke.isFromNetwork) stringResource(R.string.network) else stringResource(R.string.local)
                    )
                } else {
                    Loader(modifier = Modifier)
                }
            }
        }
    }

    companion object {
        fun newInstance(jokeId: String): JokeFragment {
            val fragment = JokeFragment()
            val bundle = Bundle().apply {
                putString("jokeId", jokeId)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}

