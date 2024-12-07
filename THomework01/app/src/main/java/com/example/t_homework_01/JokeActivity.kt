package com.example.t_homework_01

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.t_homework_01.data.Joke

class JokeActivity : ComponentActivity() {
    private val jokeViewModel: JokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleIntentData()
    }

    private fun handleIntentData() {
        val jokeCategory = intent.getStringExtra("category")
        val jokeQuestion = intent.getStringExtra("question")
        val jokeAnswer = intent.getStringExtra("answer")

        if (jokeCategory.isNullOrBlank() || jokeQuestion.isNullOrBlank() || jokeAnswer.isNullOrBlank()) {
            Toast.makeText(this, "Некорректные данные для шутки", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val newJoke = Joke(
            category = jokeCategory,
            question = jokeQuestion,
            answer = jokeAnswer
        )

        jokeViewModel.addJoke(newJoke)
        Toast.makeText(this, "Шутка успешно добавлена", Toast.LENGTH_SHORT).show()
        finish()
    }
}
