package com.example.t_homework_01

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import java.util.UUID

private val colorsBackground = listOf(YellowSoft, OrangeSoft)
private val brushBackground = Brush.verticalGradient(colors = colorsBackground)

@Composable
fun AddJokeScreen(viewModel: AddJokeViewModel, onJokeAdded: (Joke) -> Unit) {
    var category by remember { mutableStateOf("") }
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = brushBackground)
            .padding(16.dp)
    ) {
        TextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = question,
            onValueChange = { question = it },
            label = { Text("Question") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = answer,
            onValueChange = { answer = it },
            label = { Text("Answer") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(Color.White),
            onClick = {
                if (category.isNotEmpty() && question.isNotEmpty() && answer.isNotEmpty()) {
                    viewModel.addJoke(category, question, answer)
                    viewModel.newJoke.observeForever {
                        it?.let { joke ->
                            onJokeAdded(joke)
                        }
                    }
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

    private val _newJoke = MutableLiveData<Joke>()
    val newJoke: LiveData<Joke> get() = _newJoke

    fun addJoke(category: String, question: String, answer: String) {
        val joke = Joke(
            id = UUID.randomUUID().toString(),
            category = category,
            question = question,
            answer = answer,
            isFromNetwork = false
        )

        _newJoke.value = joke
    }
}
