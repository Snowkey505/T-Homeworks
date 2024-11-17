package com.example.t_homework_01

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_homework_01.data.Joke
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JokeViewModel : ViewModel() {
    private val _jokes = MutableLiveData<List<Joke>>(emptyList())
    val jokes: LiveData<List<Joke>> = _jokes

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadJokes()
    }

    fun loadJokes() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1000)
            _jokes.value = JokeRepository.getJokes()
            _isLoading.value = false
        }
    }

    fun addJoke(joke: Joke) {
        val updatedJokes = _jokes.value.orEmpty() + joke
        Log.d("JokeViewModel", "Добавлена шутка: $joke")
        _jokes.value = updatedJokes
        JokeRepository.addJoke(joke)
    }

    fun getJokeById(id: String): Joke? {
        return _jokes.value?.find { it.id == id }
    }
}

