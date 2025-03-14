package com.example.a3activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import com.example.a3activities.ui.theme._3ActivitiesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _3ActivitiesTheme {
                Greeting()
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                val intent = Intent(context, TravelActivity::class.java)
                context.startActivity(intent)
            }
        ) {
            Text("TravelActivity")
        }

        Button(
            onClick = {
                val intent=Intent(context,QuizActivity::class.java)
                context.startActivity(intent)
            }
        ) {
            Text("Quiz Activity")
        }

        Button(
            onClick = {
                val intent = Intent(context, NumberActivity::class.java)
                context.startActivity(intent)
            }
        ) {
            Text("Choose a number")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _3ActivitiesTheme {
        Greeting()
    }
}
