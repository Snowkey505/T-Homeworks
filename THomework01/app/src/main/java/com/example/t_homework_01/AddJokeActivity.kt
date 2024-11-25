package com.example.t_homework_01

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

class AddJokeActivity : ComponentActivity() {
    private val addJokeViewModel: AddJokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddJokeScreen(
                viewModel = addJokeViewModel,
                onJokeAdded = { joke ->
                    setResult(RESULT_OK, Intent().apply{})
                    finish()
                }
            )
        }
    }
}

