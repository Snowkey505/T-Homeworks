package com.example.t_homework_01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class JokeFragment : Fragment() {

    private val jokeViewModel: JokeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val jokeCategory = arguments?.getString("category") ?: "No Category"
        val jokeQuestion = arguments?.getString("question") ?: "No Question"
        val jokeAnswer = arguments?.getString("answer") ?: "No Answer"

        jokeViewModel.loadJokeData(jokeCategory, jokeQuestion, jokeAnswer)

        return ComposeView(requireContext()).apply {
            setContent {
                val category by jokeViewModel.category.observeAsState("No Category")
                val question by jokeViewModel.question.observeAsState("No Question")
                val answer by jokeViewModel.answer.observeAsState("No Answer")

                JokeDetails(category, question, answer)
            }
        }
    }
}