package com.example.t_homework_01

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.t_homework_01.data.Joke
import com.example.t_homework_01.ui.theme.OrangeSoft
import com.example.t_homework_01.ui.theme.WhiteSoft
import com.example.t_homework_01.ui.theme.YellowSoft

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jokes: MutableList<Joke> = mutableListOf(
            Joke(
                "Holiday",
                "What does Santa suffer from if he gets stuck in a chimney?",
                "Claustrophobia!"
            ),
            Joke(
                "Animals",
                "Why don't scientists trust atoms?",
                "Because they make up everything!"
            ),
            Joke("Technology", "Why was the math book sad?", "It had too many problems."),
            Joke(
                "School",
                "Why did the student eat his homework?",
                "Because the teacher told him it was a piece of cake!"
            ),
            Joke("Nature", "How do trees access the internet?", "They log in!"),
            Joke("Weather", "What does a cloud ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssswear under his raincoat?", "Thunderwear!"),
            Joke("Sports", "Why are basketball courts always wet?", "Because the players dribble!"),

        )

        setContent {
            JokesList(jokes)
        }
    }
}

@Composable
fun JokesList(data: MutableList<Joke>) {
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
        LazyColumn (
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxSize()
        )
        {
            items(data) { JokeItem(it) }
        }
    }
}

@Composable
fun JokeItem(joke: Joke) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .shadow(shape = RoundedCornerShape(10.dp), elevation = 20.dp)
            .background(WhiteSoft, shape = RoundedCornerShape(10.dp))
            .clickable {
                val intent = Intent(context, JokeActivity::class.java).apply {
                    putExtra("category", joke.category ?: "No Category")
                    putExtra("question", joke.question ?: "No Question")
                    putExtra("answer", joke.answer ?: "No Answer")
                }
                context.startActivity(intent)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                text = joke.category,
                fontSize = 12.sp,
                color = Color.Blue
            )
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                text = joke.question,
                fontSize = 20.sp,
            )
            Text(
                text = joke.answer,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}