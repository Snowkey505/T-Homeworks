package com.example.t_homework_01

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_homework_01.data.Joke
import kotlinx.coroutines.launch

class JokeViewModel : ViewModel() {
    private val _localJokes = MutableLiveData<List<Joke>>(emptyList())
    val localJokes: LiveData<List<Joke>> = _localJokes

    private val _networkJokes = MutableLiveData<List<Joke>>(emptyList())
    val networkJokes: LiveData<List<Joke>> = _networkJokes

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadNetworkJokes()
    }

    fun loadNetworkJokes() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val networkJokes = JokeRepository.getJokesFromNetwork()
                _networkJokes.value = networkJokes
            } catch (e: Exception) {
                Log.e("JokeViewModel", "Error loading network jokes: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addJoke(joke: Joke) {
        viewModelScope.launch {
            val currentJokes = _localJokes.value.orEmpty()
            _localJokes.value = currentJokes + joke
        }
    }
}