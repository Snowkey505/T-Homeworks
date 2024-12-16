package com.example.t_homework_01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.t_homework_01.data.Joke
import com.example.t_homework_01.ui.theme.OrangeSoft
import com.example.t_homework_01.ui.theme.WhiteSoft
import com.example.t_homework_01.ui.theme.YellowSoft

private val colorsBackground = listOf(YellowSoft, OrangeSoft)
private val brushBackground = Brush.verticalGradient(colors = colorsBackground)

class JokesFragment : Fragment() {

    private val jokeViewModel: JokeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val networkJokes by jokeViewModel.networkJokes.observeAsState(emptyList())
                val localJokes by jokeViewModel.localJokes.observeAsState(emptyList())
                val isLoading by jokeViewModel.isLoading.observeAsState(false)

                val allJokes = localJokes + networkJokes

                JokesList(
                    jokes = allJokes,
                    isLoading = isLoading,
                    onAddJokeClick = {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, AddJokeFragment())
                            .addToBackStack(null)
                            .commit()
                    },
                    onJokeClick = { jokeId ->
                        val jokeFragment = JokeFragment.newInstance(jokeId)
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, jokeFragment)
                            .addToBackStack(null)
                            .commit()
                    },
                    onScrollEnd = {
                        jokeViewModel.loadNetworkJokes()
                    }
                )
            }
        }
    }
}


@Composable
fun JokesList(
    jokes: List<Joke>,
    isLoading: Boolean,
    onAddJokeClick: () -> Unit,
    onJokeClick: (String) -> Unit,
    onScrollEnd: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brushBackground)
    ) {
        when {
            isLoading -> Loader(modifier = Modifier.align(Alignment.Center))
            jokes.isEmpty() -> JokesEmpty(modifier = Modifier.align(Alignment.Center))
            else -> JokesListContent(jokes, onJokeClick, onScrollEnd)
        }

        FloatingActionButton(
            onClick = onAddJokeClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("+", color = Color.White)
        }
    }
}

@Composable
fun JokesListContent(
    jokes: List<Joke>,
    onJokeClick: (String) -> Unit,
    onScrollEnd: () -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState.firstVisibleItemIndex, listState.layoutInfo.totalItemsCount) {
        val totalItemCount = listState.layoutInfo.totalItemsCount
        val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

        if (lastVisibleItemIndex == totalItemCount - 1) {
            onScrollEnd()
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxSize()
    ) {
        items(jokes) { joke ->
            JokeItem(joke = joke, onClick = { onJokeClick(joke.id) })
        }
    }
}


@Composable
fun Loader(modifier: Modifier) {
    CircularProgressIndicator(modifier)
}

@Composable
fun JokesEmpty(modifier: Modifier) {
    Text(
        text = "Шутки вышли из чата, добавьте новую!",
        modifier,
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Black
    )
}

@Composable
fun JokeItem(joke: Joke, onClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .shadow(shape = RoundedCornerShape(10.dp), elevation = 20.dp)
            .background(WhiteSoft, shape = RoundedCornerShape(10.dp))
            .clickable { onClick(joke.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row()
            {
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = joke.category,
                    fontSize = 12.sp,
                    color = Color.Blue
                )
                Text(
                    text = if (joke.isFromNetwork) stringResource(R.string.network) else stringResource(
                        R.string.local
                    ),
                    color = if (joke.isFromNetwork) Color.Green else Color.Gray,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .align(Alignment.Top)
                        .padding(4.dp)
                        .background(
                            color = if (joke.isFromNetwork) Color(0xFFE8F5E9) else Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
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