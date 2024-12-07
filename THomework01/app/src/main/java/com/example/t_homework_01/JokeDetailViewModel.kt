package com.example.t_homework_01

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.t_homework_01.data.Joke

class JokeDetailViewModel : ViewModel() {
    private val _joke = MutableLiveData<Joke?>()
    val joke: LiveData<Joke?> = _joke

    fun loadJokeById(id: String, jokeViewModel: JokeViewModel) {
        val jokeDetail = jokeViewModel.getJokeById(id)
        _joke.value = jokeDetail
    }
}

