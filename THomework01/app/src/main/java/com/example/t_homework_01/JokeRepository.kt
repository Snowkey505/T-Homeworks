package com.example.t_homework_01

import com.example.t_homework_01.data.Joke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JokeRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://v2.jokeapi.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(JokeApiService::class.java)

    suspend fun getJokesFromNetwork(page: Int, amount: Int): List<Joke> = withContext(Dispatchers.IO) {
        val response = api.fetchJokes(amount = amount, blacklistFlags = "nsfw,religious,political,racist,sexist,explicit")
        response.jokes.map { networkJoke ->
            Joke(
                category = networkJoke.category,
                question = networkJoke.setup,
                answer = networkJoke.delivery,
                isFromNetwork = true
            )
        }
    }
}
