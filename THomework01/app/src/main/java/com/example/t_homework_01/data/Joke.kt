package com.example.t_homework_01.data

import java.util.UUID

data class Joke(
    val id: String = UUID.randomUUID().toString(),
    val category: String,
    val question: String,
    val answer: String
)