package com.example.t_homework_01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.t_homework_01.data.Joke

class JokeActivity : ComponentActivity() {
    private val jokeViewModel: JokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jokeCategory = intent.getStringExtra("category") ?: "No Category"
        val jokeQuestion = intent.getStringExtra("question") ?: "No Question"
        val jokeAnswer = intent.getStringExtra("answer") ?: "No Answer"

        val newJoke = Joke(category = jokeCategory, question = jokeQuestion, answer = jokeAnswer)

        jokeViewModel.addJoke(newJoke)
        finish()
    }
}