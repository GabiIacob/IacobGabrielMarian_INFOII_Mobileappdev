package com.example.messapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.messapp.ui.theme.MessAppTheme

class SecondActivity : ComponentActivity() {
    private val messages = mutableStateListOf<Pair<String, Boolean>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val receivedMessage = intent.getStringExtra("MESSAGE") ?: ""
        messages.add(receivedMessage to false)

        setContent {
            MessAppTheme {
                ChatScreen(messages = messages, onSendMessage = ::sendMessageBack)
            }
        }
    }

    private fun sendMessageBack(message: String) {
        messages.add(message to true)

        val resultIntent = Intent().apply {
            putExtra("MESSAGE", message)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
