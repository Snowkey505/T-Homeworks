package com.example.t_homework_01

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class AddJokeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddJokeScreen(
                onJokeAdded = { joke ->
                    setResult(RESULT_OK, Intent().apply{})
                    finish()
                }
            )
        }
    }
}

