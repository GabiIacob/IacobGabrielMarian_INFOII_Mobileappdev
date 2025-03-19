package com.example.stopwatch

import android.os.Bundle
import android.os.SystemClock
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stopwatch.ui.theme.StopwatchTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StopwatchTheme {
                StopwatchApp()
            }
        }
    }
}
@Composable
fun StopwatchApp() {
    var isRunning by remember { mutableStateOf(false) }
    var timeElapsed by remember { mutableStateOf(0L) }
    var startTime by remember { mutableStateOf(0L) }
    var lastTimeUpdate by remember { mutableStateOf(0L) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (isRunning) {
                timeElapsed = SystemClock.elapsedRealtime() - startTime
                delay(10)
            }
        }
    }

    val elapsedTimeText = formatTime(timeElapsed)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Gray, shape = androidx.compose.foundation.shape.CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = elapsedTimeText,
                    fontSize = 30.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    isRunning = !isRunning
                    if (isRunning) {
                        startTime = SystemClock.elapsedRealtime() - lastTimeUpdate
                    } else {
                        lastTimeUpdate = timeElapsed
                    }
                }
            ) {
                Text(text = if (isRunning) "Stop" else "Start")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    isRunning = false
                    timeElapsed = 0L
                    lastTimeUpdate = 0L
                }
            ) {
                Text(text = "Reset")
            }
        }
    }
}
fun formatTime(timeInMillis: Long): String {
    val seconds = (timeInMillis / 1000).toInt()
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    val milliseconds = (timeInMillis % 1000) / 10
    return String.format("%02d:%02d.%02d", minutes, remainingSeconds, milliseconds)
}

@Preview(showBackground = true)
@Composable
fun StopwatchPreview() {
    StopwatchTheme {
        StopwatchApp()
    }
}
