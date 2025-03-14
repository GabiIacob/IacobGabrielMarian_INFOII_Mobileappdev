package com.example.intentionstest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.intentionstest.ui.theme.IntentionsTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntentionsTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OpenLinkIntent()
        SendEmailIntent()
        ShareTextIntent()
    }
}

@Composable
fun OpenLinkIntent() {
    val context = LocalContext.current
    val url = "https://www.google.com"

    Button(onClick = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }) {
        Text("Open Google")
    }
}

@Composable
fun SendEmailIntent() {
    val context = LocalContext.current

    Button(onClick = {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:example@example.com")
            putExtra(Intent.EXTRA_SUBJECT, "Hello")
            putExtra(Intent.EXTRA_TEXT, "This is a test email.")
        }
        context.startActivity(intent)
    }) {
        Text("Send Email")
    }
}

@Composable
fun ShareTextIntent() {
    val context = LocalContext.current
    val textToShare = "Check out this amazing app!"

    Button(onClick = {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, textToShare)
        }
        context.startActivity(Intent.createChooser(intent, "Share via"))
    }) {
        Text("Share Text")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IntentionsTestTheme {
        MainScreen(modifier = Modifier.fillMaxSize())
    }
}
