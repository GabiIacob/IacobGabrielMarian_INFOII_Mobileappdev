    package com.example.concurs

    import android.app.Activity
    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.enableEdgeToEdge
    import androidx.compose.foundation.layout.*
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.unit.dp
    import com.example.concurs.participantList

    class AddParticipantActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            val participant = intent?.getParcelableExtra<Participant>("participant")
            setContent {
                MaterialTheme {
                    AddParticipantScreen(participant)
                }
            }
        }
    }

    @Composable
    fun AddParticipantScreen(participant: Participant?) {
        var participantName by remember { mutableStateOf(participant?.name ?: "") }
        var participantSurname by remember { mutableStateOf(participant?.surname ?: "") }
        var participantScore by remember { mutableStateOf(participant?.score ?: "") }

        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (participant == null) "Add Participant" else "Edit Participant",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = participantName,
                onValueChange = { participantName = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = participantSurname,
                onValueChange = { participantSurname = it },
                label = { Text("Surname") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = participantScore,
                onValueChange = { participantScore = it },
                label = { Text("Score") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(
                onClick = {
                    if (participantName.isBlank() || participantSurname.isBlank() || participantScore.isBlank()) {
                        return@Button
                    }

                    val updatedParticipant = Participant(
                        name = participantName,
                        surname = participantSurname,
                        score = participantScore
                    )

                    if (participant != null) {
                        val index =     participantList.indexOfFirst { it.name == participant.name && it.surname == participant.surname }
                        if (index != -1) {
                            participantList[index] = updatedParticipant
                        }
                    } else {
                        participantList.add(updatedParticipant)
                    }

                    (context as? Activity)?.finish()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(if (participant == null) "Save Participant" else "Update Participant")
            }
        }
    }
