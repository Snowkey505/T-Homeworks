package com.example.t_homework_01

import com.example.t_homework_01.data.Joke

object JokeRepository {
    private val jokes = mutableListOf<Joke>()

    fun addJoke(joke: Joke) {
        jokes.add(joke)
    }

    fun getJokes(): List<Joke> = jokes
}