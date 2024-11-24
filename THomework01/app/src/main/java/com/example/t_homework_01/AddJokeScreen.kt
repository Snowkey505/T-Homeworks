package com.example.t_homework_01

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.t_homework_01.data.Joke
import com.example.t_homework_01.ui.theme.OrangeSoft
import com.example.t_homework_01.ui.theme.YellowSoft

private val colorsBackground = listOf(YellowSoft, OrangeSoft)
private val brushBackground = Brush.verticalGradient(colors = colorsBackground)

@Composable
fun AddJokeScreen(
    viewModel: AddJokeViewModel,
    onJokeAdded: (Joke) -> Unit
) {
    val category by viewModel.category.observeAsState("")
    val question by viewModel.question.observeAsState("")
    val answer by viewModel.answer.observeAsState("")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = brushBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = category,
            onValueChange = { viewModel.updateCategory(it) },
            label = { Text("Категория") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = question,
            onValueChange = { viewModel.updateQuestion(it) },
            label = { Text("Вопрос") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = answer,
            onValueChange = { viewModel.updateAnswer(it) },
            label = { Text("Ответ") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            colors = ButtonDefaults.buttonColors(Color.White),
            onClick = {
                if (viewModel.isInputValid()) {
                    val newJoke = viewModel.getNewJoke()
                    onJokeAdded(newJoke)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Добавить шутку",
                color = Color.Black
            )
        }
    }
}


class AddJokeViewModel : ViewModel() {
    private val _category = MutableLiveData("")
    val category: LiveData<String> = _category

    private val _question = MutableLiveData("")
    val question: LiveData<String> = _question

    private val _answer = MutableLiveData("")
    val answer: LiveData<String> = _answer

    fun updateCategory(newCategory: String) {
        _category.value = newCategory
    }

    fun updateQuestion(newQuestion: String) {
        _question.value = newQuestion
    }

    fun updateAnswer(newAnswer: String) {
        _answer.value = newAnswer
    }

    fun isInputValid(): Boolean {
        return !_category.value.isNullOrBlank() &&
                !_question.value.isNullOrBlank() &&
                !_answer.value.isNullOrBlank()
    }

    fun getNewJoke(): Joke {
        return Joke(
            category = _category.value ?: "",
            question = _question.value ?: "",
            answer = _answer.value ?: ""
        )
    }
}
