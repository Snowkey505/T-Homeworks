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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.t_homework_01.data.Joke
import com.example.t_homework_01.ui.theme.OrangeSoft
import com.example.t_homework_01.ui.theme.YellowSoft

@Composable
fun AddJokeScreen(onJokeAdded: (Joke) -> Unit) {
    var category by remember { mutableStateOf("") }
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    val colorsBackground = listOf(YellowSoft, OrangeSoft)
    val brushBackground = Brush.verticalGradient(colors = colorsBackground)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = brushBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Категория") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = question,
            onValueChange = { question = it },
            label = { Text("Вопрос") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = answer,
            onValueChange = { answer = it },
            label = { Text("Ответ") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            colors = ButtonDefaults.buttonColors(Color.White),
            onClick = {
                if (category.isNotBlank() && question.isNotBlank() && answer.isNotBlank()) {
                    val newJoke = Joke(
                        category = category,
                        question = question,
                        answer = answer
                    )
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
