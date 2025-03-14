package com.example.messapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messapp.ui.theme.MessAppTheme

class MainActivity : ComponentActivity() {
    private val messages = mutableStateListOf<Pair<String, Boolean>>()

    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val message = result.data?.getStringExtra("MESSAGE")
                message?.let {
                    messages.add(it to false)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessAppTheme {
                ChatScreen(messages = messages, onSendMessage = ::sendMessage)
            }
        }
    }

    private fun sendMessage(message: String) {
        messages.add(message to true)
        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra("MESSAGE", message)
        }
        startActivityForResult.launch(intent)
    }
}

@Composable
fun ChatScreen(messages: List<Pair<String, Boolean>>, onSendMessage: (String) -> Unit) {
    var message by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages) { (msg, isSent) ->
                MessageBubble(message = msg, isSent = isSent)
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            )

            Button(onClick = {
                if (message.isNotBlank()) {
                    onSendMessage(message)
                    message = ""
                }
            }) {
                Text("Send")
            }
        }
    }
}

@Composable
fun MessageBubble(message: String, isSent: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isSent) Arrangement.End else Arrangement.Start
    ) {
        Text(
            text = message,
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    MessAppTheme {
        ChatScreen(
            messages = listOf("Hello!" to true, "How are you?" to false),
            onSendMessage = {}
        )
    }
}
