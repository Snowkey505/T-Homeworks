package com.example.t_homework_01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.t_homework_01.data.Joke

class JokeFragment : Fragment() {

    companion object {
        fun newInstance(jokeId: String): JokeFragment {
            val fragment = JokeFragment()
            val args = Bundle().apply {
                putString("jokeId", jokeId)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var joke: Joke
    private val jokeViewModel: JokeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val jokeId = arguments?.getString("jokeId")

                jokeId?.let {
                    jokeViewModel.getJokeById(it)?.let { foundJoke ->
                        joke = foundJoke
                        JokeDetails(
                            category = joke.category,
                            question = joke.question,
                            answer = joke.answer,
                            source = if (joke.isFromNetwork) stringResource(R.string.network) else stringResource(R.string.local)
                        )
                    } ?: run {
                        Text(text = "Шутка не найдена")
                    }
                }
            }
        }
    }
}