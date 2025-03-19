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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VotingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var candidate1 by remember { mutableStateOf("") }
    var candidate2 by remember { mutableStateOf("") }

    val activity = (context as? ComponentActivity)
    val data = activity?.intent
    candidate1 = data?.getStringExtra("CANDIDATE_1") ?: ""
    candidate2 = data?.getStringExtra("CANDIDATE_2") ?: ""

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(" Voting App", style = MaterialTheme.typography.headlineSmall)

        Button(
            onClick = {
                val intent = Intent(context, CreateActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Vote")
        }

        Button(
            onClick = {
                val intent = Intent(context, VotingActivity::class.java).apply {
                    putExtra("CANDIDATE_1", candidate1)
                    putExtra("CANDIDATE_2", candidate2)
                }
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = candidate1.isNotEmpty() && candidate2.isNotEmpty()
        ) {
            Text("Vote")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    VotingTheme {
        MainScreen()
    }
}
