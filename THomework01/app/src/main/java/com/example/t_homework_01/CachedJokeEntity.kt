package com.example.t_homework_01

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_jokes")
data class CachedJokeEntity(
    @PrimaryKey val id: String,
    val category: String,
    val question: String,
    val answer: String,
    val timestamp: Long
)