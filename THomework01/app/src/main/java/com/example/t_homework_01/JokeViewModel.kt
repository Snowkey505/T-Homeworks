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

    private var currentPage = 1
    private val jokesPerPage = 10

    private val _allJokes = MutableLiveData<List<Joke>>(emptyList())
    val allJokes: LiveData<List<Joke>> = _allJokes

    init {
        loadNetworkJokes()
    }

    fun loadNetworkJokes() {
        if (_isLoading.value == true) return

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val networkJokes = JokeRepository.getNetworkJokes(amount = jokesPerPage)
                _networkJokes.value = _networkJokes.value.orEmpty() + networkJokes
                _allJokes.value = _allJokes.value.orEmpty() + networkJokes
                currentPage++
            } catch (e: Exception) {
                Log.e("JokeViewModel", "Error loading network jokes: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getJokeById(id: String): Joke? {
        return _allJokes.value?.firstOrNull { it.id == id }
    }

    fun addJoke(joke: Joke) {
        viewModelScope.launch {
            val updatedLocalJokes = _localJokes.value.orEmpty() + joke
            _localJokes.value = updatedLocalJokes
            val updatedAllJokes = _allJokes.value.orEmpty() + joke
            _allJokes.value = updatedAllJokes
        }
    }
}
