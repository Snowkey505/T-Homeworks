package com.example.t_homework_01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

class AddJokeActivity : ComponentActivity() {
    private val jokeViewModel: JokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddJokeScreen(onJokeAdded = { joke ->
                jokeViewModel.addJoke(joke)
                finish()
            })
        }
    }
}
