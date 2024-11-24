package com.example.t_homework_01

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.t_homework_01.ui.theme.OrangeSoft
import com.example.t_homework_01.ui.theme.WhiteSoft
import com.example.t_homework_01.ui.theme.YellowSoft
import androidx.compose.ui.Alignment

private val colorsBackground = listOf(YellowSoft, OrangeSoft)
private val brushBackground = Brush.verticalGradient(colors = colorsBackground)

@Composable
@Preview(showSystemUi = true)
fun JokeDetailsPreview(){
    JokeDetails(
        category = "Category",
        question = "Is it a question?",
        answer = "Yes!"
    )
}

@Composable
fun JokeDetails(category: String, question: String, answer: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = brushBackground
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