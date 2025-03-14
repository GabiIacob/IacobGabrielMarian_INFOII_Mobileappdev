package com.example.a3activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a3activities.ui.theme._3ActivitiesTheme
import kotlin.random.Random

class NumberActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberScreen()
        }
    }
}

@Composable
fun NumberScreen() {
    var userGuess by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf("") }
    val randomNumber = remember { Random.nextInt(1, 11) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Ghici numarul intre 1 si 10")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = userGuess,
            onValueChange = { userGuess = it },
            label = { Text("Introduceti un numar") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (userGuess.toIntOrNull() == randomNumber) {
                    resultMessage = "Corect!"
                } else {
                    resultMessage = "Incorect! Numarul corect era $randomNumber"
                }
            }
        ) {
            Text("Verifica")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = resultMessage)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (context is ComponentActivity) {
                    context.finish()
                }
            }
        ) {
            Text("Inapoi la Quiz")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NumberScreenPreview() {
    _3ActivitiesTheme {
        NumberScreen()
    }
}
