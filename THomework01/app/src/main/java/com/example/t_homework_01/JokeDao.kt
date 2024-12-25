package com.example.t_homework_01

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface  JokeDao{
    @Query("SELECT * FROM local_jokes")
    fun getLocalJokes(): LiveData<List<LocalJokeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalJoke(joke: LocalJokeEntity)

    @Query("SELECT * FROM cached_jokes")
    fun getCachedJokes(): LiveData<List<CachedJokeEntity>>

    @Query("DELETE FROM cached_jokes WHERE timestamp < :validTime")
    suspend fun clearOldCache(validTime: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedJokes(jokes: List<CachedJokeEntity>)
}