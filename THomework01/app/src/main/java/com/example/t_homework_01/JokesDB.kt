package com.example.t_homework_01

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity(tableName = "local_jokes")
data class LocalJokeEntity(
    @PrimaryKey val id: String,
    val category: String,
    val question: String,
    val answer: String
)

@Entity(tableName = "cached_jokes")
data class CachedJokeEntity(
    @PrimaryKey val id: String,
    val category: String,
    val question: String,
    val answer: String,
    val timestamp: Long
)

@Dao
interface JokeDao {
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

@Database(entities = [LocalJokeEntity::class, CachedJokeEntity::class], version = 1)
abstract class JokeDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao

    companion object {
        @Volatile private var instance: JokeDatabase? = null

        fun getInstance(context: Context): JokeDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    JokeDatabase::class.java,
                    "joke_database"
                ).build().also { instance = it }
            }
        }
    }
}
