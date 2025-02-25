package com.example.lab4

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab4.ui.theme.Lab4Theme
import java.util.Calendar
import androidx.compose.ui.platform.LocalContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4Theme {
                light()
            }
        }
    }
}
@Composable
fun light(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var isAutomaticMode by remember { mutableStateOf(false) }
    var isLightOn by remember { mutableStateOf(false) }
    var backgroundColor by remember { mutableStateOf(Color.Gray) }

    val colorList = listOf(Color.Yellow, Color.White, Color.Blue, Color.Red, Color.Green)
    var selectedColorIndex by remember { mutableStateOf(0) }

    fun getCurrentHour(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    fun updateLightState(hour: Int, context: Context) {
        when {
            hour in 7 until 18 -> {
                backgroundColor = Color.White
                val text = "Good morning! Do you need more light?"
                val duration = Toast.LENGTH_SHORT
                Toast.makeText(context, text, duration).show()
            }
            hour in 18 until 20 -> {
                backgroundColor = Color(0xFFFFA500)
            }
            else -> {
                backgroundColor = Color.Gray
                val text = "Good night! The light has turned off automatically."
                val duration = Toast.LENGTH_SHORT
                Toast.makeText(context, text, duration).show()
            }
        }
    }

    if (isAutomaticMode) {
        val currentHour = getCurrentHour()
        updateLightState(currentHour, context)
    } else {
        if (isLightOn) {
            backgroundColor = colorList[selectedColorIndex]
        } else {
            backgroundColor = Color.Gray
        }
    }

    Column(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (!isAutomaticMode) {
                isLightOn = !isLightOn
                if (isLightOn) {
                    backgroundColor = Color.Yellow
                } else {
                    backgroundColor = Color.Gray
                }
            }
        }) {
            Text(text = if (isLightOn) "Turn Off" else "Turn On")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { isAutomaticMode = !isAutomaticMode }) {
            Text(text = if (isAutomaticMode) "Disable Automatic Mode" else "Enable Automatic Mode")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (isLightOn) {
                if (selectedColorIndex == colorList.size - 1) {
                    selectedColorIndex = 0
                } else {
                    selectedColorIndex += 1
                }
            }
        }) {
            Text(text = "Change Light Color")
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab4Theme {
        light()
    }
}