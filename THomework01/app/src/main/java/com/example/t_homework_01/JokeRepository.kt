package com.example.t_homework_01

import android.content.Context
import android.util.Log
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.t_homework_01.data.Joke
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Entity(tableName = "jokes")
data class JokeEntity(
    @PrimaryKey val id: String,
    val category: String,
    val question: String,
    val answer: String,
    val isFromNetwork: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "network_cache")
data class NetworkJokeEntity(
    @PrimaryKey val id: String,
    val category: String,
    val question: String,
    val answer: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface JokeDao {
    @Query("SELECT * FROM jokes")
    suspend fun getLocalJokes(): List<JokeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalJoke(joke: JokeEntity)

    @Query("SELECT * FROM network_cache ORDER BY timestamp DESC")
    suspend fun getNetworkJokes(): List<NetworkJokeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNetworkJokes(jokes: List<NetworkJokeEntity>)

    @Query("DELETE FROM network_cache WHERE timestamp < :expirationTime")
    suspend fun clearExpiredCache(expirationTime: Long)
}

@Database(entities = [JokeEntity::class, NetworkJokeEntity::class], version = 1)
abstract class JokeDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao
}

object JokeRepository {
    private lateinit var database: JokeDatabase
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://v2.jokeapi.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api: JokeApiService = retrofit.create(JokeApiService::class.java)

    suspend fun getNetworkJokes(amount: Int): List<Joke> {
        return try {
            val response = api.fetchJokes(amount = amount)
            response.jokes.map {
                Joke(
                    category = it.category,
                    question = it.setup,
                    answer = it.delivery,
                    isFromNetwork = true
                )
            }
        } catch (e: Exception) {
            Log.e("JokeRepository", "Error fetching jokes: ${e.message}")
            emptyList()
        }
    }

    fun initDatabase(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            JokeDatabase::class.java,
            "joke_database"
        ).build()
    }

    suspend fun getLocalJokes(): List<Joke> {
        return database.jokeDao().getLocalJokes().map {
            Joke(
                id = it.id,
                category = it.category,
                question = it.question,
                answer = it.answer,
                isFromNetwork = it.isFromNetwork
            )
        }
    }

    suspend fun addLocalJoke(joke: Joke) {
        database.jokeDao().insertLocalJoke(
            JokeEntity(
                id = joke.id,
                category = joke.category,
                question = joke.question,
                answer = joke.answer,
                isFromNetwork = joke.isFromNetwork
            )
        )
    }

    suspend fun getCachedNetworkJokes(): List<Joke> {
        return database.jokeDao().getNetworkJokes().map {
            Joke(
                id = it.id,
                category = it.category,
                question = it.question,
                answer = it.answer,
                isFromNetwork = true
            )
        }
    }

    suspend fun cacheNetworkJokes(jokes: List<Joke>) {
        val entities = jokes.map {
            NetworkJokeEntity(
                id = it.id,
                category = it.category,
                question = it.question,
                answer = it.answer
            )
        }
        database.jokeDao().insertNetworkJokes(entities)
    }

    suspend fun clearExpiredCache() {
        val expirationTime = System.currentTimeMillis() - 24 * 60 * 60 * 1000 // 24 hours
        database.jokeDao().clearExpiredCache(expirationTime)
    }
}
