package com.example.t_homework_01

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.t_homework_01.data.Joke
import com.example.t_homework_01.ui.theme.OrangeSoft
import com.example.t_homework_01.ui.theme.WhiteSoft
import com.example.t_homework_01.ui.theme.YellowSoft

class JokeActivity : ComponentActivity() {
    private val jokeViewModel: JokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jokeCategory = intent?.getStringExtra("category") ?: "No Category"
        val jokeQuestion = intent?.getStringExtra("question") ?: "No Question"
        val jokeAnswer = intent?.getStringExtra("answer") ?: "No Answer"

        jokeViewModel.loadJokeData(jokeCategory, jokeQuestion, jokeAnswer)

        setContent {
            val category by jokeViewModel.category.observeAsState("No Category")
            val question by jokeViewModel.question.observeAsState("No Question")
            val answer by jokeViewModel.answer.observeAsState("No Answer")

            JokeDetails(category, question, answer)
        }
    }

    companion object {
        fun createActivity(context: Context, joke: Joke) {
            val intent = Intent(context, JokeActivity::class.java).apply {
                putExtra("category", joke.category)
                putExtra("question", joke.question)
                putExtra("answer", joke.answer)
            }
            context.startActivity(intent)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun JokeDetails(
    category: String = "Category",
    question: String = "Is it a question?",
    answer: String = "Yes!"
) {
    val colorsBackground = listOf(
        YellowSoft,
        OrangeSoft
    )
    val brushBackground = Brush.verticalGradient(colors = colorsBackground)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = brushBackground,
                shape = RectangleShape
            )
    ) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .padding(20.dp),
            colors = CardDefaults.cardColors(WhiteSoft),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(5.dp)
            )
            {
                Text(
                    text = category,
                    fontSize = 20.sp,
                    color = Color.Blue,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    modifier = Modifier
                        .wrapContentHeight()
                        .border(width = 2.dp, color = OrangeSoft, shape = RoundedCornerShape(10.dp))
                        .background(Color.White)
                ) {
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            text = question,
                            fontSize = 30.sp
                        )
                    }

                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp)
                                .align(Alignment.End),
                            text = answer,
                            fontSize = 40.sp
                        )
                    }

                }

            }

        }
    }
}