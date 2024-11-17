package com.example.t_homework_01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class JokeFragment : Fragment() {
    private val jokeViewModel: JokeViewModel by activityViewModels()
    private var jokeId: String? = null

    companion object {
        private const val ARG_JOKE_ID = "joke_id"

        fun newInstance(jokeId: String): JokeFragment {
            val fragment = JokeFragment()
            val args = Bundle()
            args.putString(ARG_JOKE_ID, jokeId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        jokeId = arguments?.getString(ARG_JOKE_ID)
        val joke = jokeId?.let { jokeViewModel.getJokeById(it) }

        return ComposeView(requireContext()).apply {
            setContent {
                if (joke != null) {
                    JokeDetails(
                        category = joke.category,
                        question = joke.question,
                        answer = joke.answer
                    )
                } else {
                    Text("Шутка не найдена", modifier = Modifier.fillMaxSize(), color = Color.Black)
                }
            }
        }
    }
}
