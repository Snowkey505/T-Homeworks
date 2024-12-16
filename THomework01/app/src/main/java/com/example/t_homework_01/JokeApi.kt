package com.example.t_homework_01

import retrofit2.http.GET
import retrofit2.http.Query

interface JokeApiService {
    @GET("joke/Any")
    suspend fun fetchJokes(
        @Query("blacklistFlags") blacklistFlags: String = "nsfw,religious,political,racist,sexist,explicit",
        @Query("type") type: String = "twopart",
        @Query("amount") amount: Int = 10
    ): JokeResponse
}

data class JokeResponse(
    val jokes: List<NetworkJoke>
)

data class NetworkJoke(
    val category: String,
    val setup: String,
    val delivery: String
)