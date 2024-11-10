package com.example.t_homework_01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.t_homework_01.data.Joke
import com.example.t_homework_01.ui.theme.OrangeSoft
import com.example.t_homework_01.ui.theme.WhiteSoft
import com.example.t_homework_01.ui.theme.YellowSoft
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var jokeViewModel: JokeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jokeViewModel = ViewModelProvider(this).get(JokeViewModel::class.java)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, JokesFragment())
                .commit()
        }
    }
}

class JokesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
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
                    Joke("Weather", "What does a cloud wear under his raincoat?", "Thunderwear!"),
                    Joke(
                        "Sports",
                        "Why are basketball courts always wet?",
                        "Because the players dribble!"
                    )
                )
                JokesList(jokes)
            }
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
        LazyColumn(
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
    val activity = context as AppCompatActivity

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .shadow(shape = RoundedCornerShape(10.dp), elevation = 20.dp)
            .background(WhiteSoft, shape = RoundedCornerShape(10.dp))
            .clickable {
                val bundle = Bundle().apply {
                    putString("category", joke.category)
                    putString("question", joke.question)
                    putString("answer", joke.answer)
                }
                val jokeFragment = JokeFragment().apply {
                    arguments = bundle
                }

                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, jokeFragment)
                    .addToBackStack(null)
                    .commit()
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