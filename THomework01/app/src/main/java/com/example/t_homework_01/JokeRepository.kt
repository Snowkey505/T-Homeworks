package com.example.t_homework_01

import androidx.lifecycle.LiveData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID


class JokeRepository(private val jokeDao: JokeDao) {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://v2.jokeapi.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(JokeApiService::class.java)

    val localJokes: LiveData<List<LocalJokeEntity>> = jokeDao.getLocalJokes()
    val cachedJokes: LiveData<List<CachedJokeEntity>> = jokeDao.getCachedJokes()

    suspend fun addLocalJoke(joke: LocalJokeEntity) {
        jokeDao.insertLocalJoke(joke)
    }

    suspend fun updateCache(networkJokes: List<CachedJokeEntity>) {
        jokeDao.insertCachedJokes(networkJokes)
    }

    suspend fun clearOldCache(validTime: Long) {
        jokeDao.clearOldCache(validTime)
    }

    fun fetchCachedJokes(): LiveData<List<CachedJokeEntity>> {
        return cachedJokes
    }

    suspend fun fetchNetworkJokes(): List<CachedJokeEntity> {
        val response = api.fetchJokes()
        return response.jokes.map { joke ->
            CachedJokeEntity(
                id = UUID.randomUUID().toString(),
                category = joke.category,
                question = joke.setup,
                answer = joke.delivery,
                timestamp = System.currentTimeMillis()
            )
        }
    }
}
