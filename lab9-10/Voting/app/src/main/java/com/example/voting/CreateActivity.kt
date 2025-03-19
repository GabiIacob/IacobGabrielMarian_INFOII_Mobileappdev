package com.example.voting

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voting.ui.theme.VotingTheme

class CreateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VotingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CreateVoteScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CreateVoteScreen(modifier: Modifier = Modifier) {
    var candidate1 by remember { mutableStateOf("") }
    var candidate2 by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = candidate1,
            onValueChange = { candidate1 = it },
            label = { Text("Candidate 1") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = candidate2,
            onValueChange = { candidate2 = it },
            label = { Text("Candidate 2") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val intent = Intent(context, MainActivity::class.java).apply {
                    putExtra("CANDIDATE_1", candidate1)
                    putExtra("CANDIDATE_2", candidate2)
                }
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Vote")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateVoteScreen() {
    VotingTheme {
        CreateVoteScreen()
    }
}
