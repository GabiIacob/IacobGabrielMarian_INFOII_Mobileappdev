package com.example.a3activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity

class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizScreen()
        }
    }
}
@Composable
fun QuizScreen() {
    val correctAnswer = "Leu"
    val imageResId = R.drawable.leu
    val options = listOf("Leu", "Elefant", "Delfin", "Papagal")

    var selectedAnswer by remember { mutableStateOf("") }
    var isAnswerCorrect by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Animal",
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        options.forEach { option ->
            Button(
                onClick = {
                    selectedAnswer = option
                    isAnswerCorrect = option == correctAnswer
                    showResult = true
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(option)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showResult) {
            if (isAnswerCorrect) {
                Text("Raspuns corect! Felicitari!", color = androidx.compose.ui.graphics.Color.Green)
            } else {
                Text("Raspuns gresit. Incearca din nou.", color = androidx.compose.ui.graphics.Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val intent = Intent(context, NumberActivity::class.java)
                context.startActivity(intent)
            }
        ) {
            Text("Mergi la Ghici Numarul")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (context is ComponentActivity) {
                    context.finish()
                }
            }
        ) {
            Text("Inapoi la Travel")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    QuizScreen()
}
