package com.example.t_homework_01

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JokeViewModel : ViewModel() {

    private val _category = MutableLiveData("No Category")
    val category: LiveData<String> = _category

    private val _question = MutableLiveData("No Question")
    val question: LiveData<String> = _question

    private val _answer = MutableLiveData("No Answer")
    val answer: LiveData<String> = _answer

    fun loadJokeData(category: String, question: String, answer: String) {
        _category.value = category
        _question.value = question
        _answer.value = answer
    }
}
