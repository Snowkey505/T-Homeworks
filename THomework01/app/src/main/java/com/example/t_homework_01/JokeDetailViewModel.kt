package com.example.t_homework_01

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.t_homework_01.data.Joke

class JokeDetailViewModel(private val jokeRepository: JokeRepository) : ViewModel() {
    private val _joke = MutableLiveData<Joke?>()
    val joke: LiveData<Joke?> = _joke

    fun loadJokeById(id: String) {
        val allJokes = mutableListOf<Joke>()

        jokeRepository.localJokes.observeForever { localJokes ->
            allJokes.addAll(localJokes.map {
                Joke(it.id, it.category, it.question, it.answer, isFromNetwork = false)
            })
        }

        jokeRepository.cachedJokes.observeForever { cachedJokes ->
            allJokes.addAll(cachedJokes.map {
                Joke(it.id, it.category, it.question, it.answer, isFromNetwork = true)
            })
        }

        val jokeDetail = allJokes.find { it.id == id }

        _joke.value = jokeDetail
    }
}