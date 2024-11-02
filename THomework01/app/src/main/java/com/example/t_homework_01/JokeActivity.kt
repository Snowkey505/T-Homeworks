package com.example.t_homework_01

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.t_homework_01.data.Joke
import com.example.t_homework_01.ui.theme.OrangeSoft
import com.example.t_homework_01.ui.theme.WhiteSoft
import com.example.t_homework_01.ui.theme.YellowSoft

class JokeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jokeCategory = intent?.getStringExtra("category") ?: "Unknown Category"
        val jokeQuestion = intent?.getStringExtra("question") ?: "No Question"
        val jokeAnswer = intent?.getStringExtra("answer") ?: "No Answer"

        setContent {
            JokeDetails(jokeCategory, jokeQuestion, jokeAnswer)
        }
    }
}

@Composable
fun JokeDetails(category: String, question: String, answer: String) {
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
                .fillMaxSize()
                .padding(20.dp),
            colors = CardDefaults.cardColors(WhiteSoft),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            )
            {
                Text(
                    text = category,
                    fontSize = 20.sp,
                    color = Color.Blue
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(width = 2.dp, color = OrangeSoft, shape = RoundedCornerShape(10.dp))
                        .background(Color.White)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(5.dp),
                        text = question,
                        fontSize = 30.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier
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