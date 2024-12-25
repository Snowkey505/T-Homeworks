package com.example.t_homework_01

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.t_homework_01.data.Joke
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID

class JokeViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JokeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JokeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



class JokeViewModel(application: Application) : AndroidViewModel(application) {
    private val jokeDao = JokeDatabase.getInstance(application).jokeDao()
    private val repository = JokeRepository(jokeDao)

    val localJokes: LiveData<List<LocalJokeEntity>> = repository.localJokes
    val cachedJokes: LiveData<List<CachedJokeEntity>> = repository.cachedJokes

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage

    fun addLocalJoke(joke: Joke) {
        viewModelScope.launch {
            repository.addLocalJoke(LocalJokeEntity(joke.id, joke.category, joke.question, joke.answer))
        }
    }

    fun loadJokes() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.localJokes.value?.let { localJokesList ->
                    if (localJokesList.isNullOrEmpty()) {
                        repository.cachedJokes.value?.let { cachedJokesList ->
                            if (cachedJokesList.isNullOrEmpty()) {
                                loadNetworkJokes()  // Загрузка шуток из сети
                            } else {
                                _statusMessage.value = "Шутки из кэша (сеть недоступна)"
                            }
                        }
                    } else {
                        _statusMessage.value = "Шутки из локальной базы данных"
                    }
                }
            } catch (e: Exception) {
                _statusMessage.value = "Ошибка при загрузке шуток: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun loadNetworkJokes() {
        try {
            val networkJokes = repository.fetchNetworkJokes()
            repository.updateCache(networkJokes)
            _statusMessage.value = "Шутки загружены из сети"
        } catch (e: Exception) {
            _statusMessage.value = "Ошибка при загрузке данных с сети: ${e.message}"
        }
    }

    fun clearOldCache() {
        viewModelScope.launch {
            repository.clearOldCache(System.currentTimeMillis() - 24 * 60 * 60 * 1000)
        }
    }
}
