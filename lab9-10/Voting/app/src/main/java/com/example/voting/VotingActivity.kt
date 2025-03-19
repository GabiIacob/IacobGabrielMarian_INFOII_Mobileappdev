package com.example.voting

import android.os.Bundle
import android.widget.Toast
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
import kotlin.random.Random

class VotingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VotingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    VotingScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun VotingScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val candidate1 = (context as? VotingActivity)?.intent?.getStringExtra("CANDIDATE_1") ?: "Candidate 1"
    val candidate2 = (context as? VotingActivity)?.intent?.getStringExtra("CANDIDATE_2") ?: "Candidate 2"

    val users = listOf(
        User("1234567890123"),
        User("2345678901234"),
        User("3456789012345")
    )

    var candidate1Votes by remember { mutableStateOf(0) }
    var candidate2Votes by remember { mutableStateOf(0) }

    var selectedCandidate by remember { mutableStateOf("") }
    var cnpInput by remember { mutableStateOf("") }
    var otpInput by remember { mutableStateOf("") }
    var generatedOtp by remember { mutableStateOf("") }
    var isValidUser by remember { mutableStateOf(false) }
    var isOtpVerified by remember { mutableStateOf(false) }

    var votedCnpList by remember { mutableStateOf(mutableListOf<String>()) }

    fun generateOtp(cnp: String) {
        val user = users.find { it.cnp == cnp }
        if (user != null) {
            generatedOtp = Random.nextInt(100000, 999999).toString()
            isValidUser = true
            Toast.makeText(context, "Generated OTP: $generatedOtp", Toast.LENGTH_LONG).show()
        } else {
            isValidUser = false
            Toast.makeText(context, "Invalid CNP.", Toast.LENGTH_SHORT).show()
        }
    }

    fun verifyOtp(inputOtp: String): Boolean {
        return inputOtp == generatedOtp
    }

    fun submitVote() {
        if (selectedCandidate == candidate1) {
            candidate1Votes++
        } else if (selectedCandidate == candidate2) {
            candidate2Votes++
        }
        votedCnpList.add(cnpInput)
    }

    fun hasVoted(cnp: String): Boolean {
        return votedCnpList.contains(cnp)
    }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Vote for your candidate:", style = MaterialTheme.typography.headlineSmall)

        RadioButtonGroup(
            options = listOf(candidate1, candidate2),
            selectedOption = selectedCandidate,
            onOptionSelected = { selectedCandidate = it }
        )

        OutlinedTextField(
            value = cnpInput,
            onValueChange = { cnpInput = it },
            label = { Text("Enter CNP") },
            modifier = Modifier.fillMaxWidth()
        )

        if (hasVoted(cnpInput)) {
            Text("You have already voted!", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
        } else {
            Button(
                onClick = {
                    generateOtp(cnpInput)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Generate OTP")
            }
        }

        if (isValidUser) {
            OutlinedTextField(
                value = otpInput,
                onValueChange = { otpInput = it },
                label = { Text("Enter OTP") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (verifyOtp(otpInput)) {
                        isOtpVerified = true
                        Toast.makeText(context, "OTP Verified!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Invalid OTP.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Verify OTP")
            }
        }

        Button(
            onClick = {
                if (isOtpVerified && selectedCandidate.isNotEmpty() && !hasVoted(cnpInput)) {
                    submitVote()
                    Toast.makeText(context, "Vote submitted successfully for $selectedCandidate!", Toast.LENGTH_SHORT).show()
                } else if (hasVoted(cnpInput)) {
                    Toast.makeText(context, "You have already voted!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please complete the process before voting.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isOtpVerified && selectedCandidate.isNotEmpty() && !hasVoted(cnpInput)
        ) {
            Text("Submit Vote")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Results: ", style = MaterialTheme.typography.headlineSmall)
        Text("$candidate1: $candidate1Votes votes", style = MaterialTheme.typography.bodyMedium)
        Text("$candidate2: $candidate2Votes votes", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun RadioButtonGroup(options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    Column {
        options.forEach { text ->
            Row(modifier = Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) }
                )
                Text(text = text, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewVotingScreen() {
    VotingTheme {
        VotingScreen()
    }
}
