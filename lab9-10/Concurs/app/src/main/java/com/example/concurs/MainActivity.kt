package com.example.concurs

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.concurs.AddParticipantActivity
import com.example.concurs.Participant

val participantList = mutableStateListOf<Participant>()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (participantList.isEmpty()) {
            participantList.addAll(
                listOf(
                    Participant(name = "Ion", surname = "Stancu", score = "85"),
                    Participant(name = "Iancu", surname = "Bogdan", score = "90")
                )
            )
        }
        setContent {
            MaterialTheme {
                ParticipantListScreen()
            }
        }
    }
}

@Composable
fun ParticipantListScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var sortAscending by remember { mutableStateOf(true) }
    var filterText by remember { mutableStateOf(TextFieldValue("")) }

    val filteredParticipants = participantList.filter { participant ->
        (participant.score.toIntOrNull() ?: 0) >= (filterText.text.toIntOrNull() ?: 0)
    }

    val sortedParticipants = if (sortAscending) {
        filteredParticipants.sortedBy { it.score.toIntOrNull() ?: 0 }
    } else {
        filteredParticipants.sortedByDescending { it.score.toIntOrNull() ?: 0 }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Participant List",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 24.sp,
                color = Color.Black
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = filterText,
                onValueChange = { filterText = it },
                label = { Text("Filter by score") },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { sortAscending = !sortAscending }) {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Sort", tint = Color.Black)
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(sortedParticipants) { participant ->
                ParticipantItem(participant = participant)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .height(48.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, AddParticipantActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(horizontal = 8.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Add Participant", color = Color.Black)
            }
        }
    }
}

@Composable
fun ParticipantItem(participant: Participant) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${participant.name} ${participant.surname}",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
            )
            Text(
                text = "Score: ${participant.score}",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )
        }
        IconButton(onClick = {
            val intent = Intent(context, AddParticipantActivity::class.java).apply {
                putExtra("participant", participant)
            }
            context.startActivity(intent)
        }) {
            Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Color.Black)
        }
        IconButton(onClick = {
            participantList.remove(participant)
        }) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
        }
    }
}