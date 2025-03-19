package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TicTacToeTheme {
                GameState()
            }
        }
    }
}

@Composable
fun GameState() {
    var grid by remember { mutableStateOf(Array(3) { Array(3) { "" } }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf<String?>(null) }

    fun onCellClick(row: Int, col: Int) {
        if (grid[row][col].isEmpty() && winner == null) {
            grid[row][col] = currentPlayer
            if (checkWinner(grid)) {
                winner = currentPlayer
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GameBoard(grid, currentPlayer, ::onCellClick)

        if (winner != null) {
            AlertDialog(
                onDismissRequest = {},
                confirmButton = {
                    Button(onClick = {
                        grid = Array(3) { Array(3) { "" } }
                        currentPlayer = "X"
                        winner = null
                    }) {
                        Text("Play Again")
                    }
                },
                title = { Text("We have a winner!") },
                text = { Text("Player $winner wins!") }
            )
        }
    }
}

fun checkWinner(grid: Array<Array<String>>): Boolean {
    for (i in 0..2) {
        if ((grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2] && grid[i][0].isNotEmpty()) ||
            (grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i] && grid[0][i].isNotEmpty())) {
            return true
        }
    }
    if ((grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[0][0].isNotEmpty()) ||
        (grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0] && grid[0][2].isNotEmpty())) {
        return true
    }
    return false
}

@Composable
fun GameBoard(grid: Array<Array<String>>, currentPlayer: String, onCellClick: (Int, Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Player $currentPlayer's Turn", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            for (row in 0..2) {
                Row {
                    for (col in 0..2) {
                        GameCell(cellValue = grid[row][col], onClick = { onCellClick(row, col) })
                    }
                }
            }
        }
    }
}

@Composable
fun GameCell(cellValue: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(4.dp)
            .background(Color.Gray, shape = androidx.compose.foundation.shape.CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (cellValue == "X") {
            Image(painter = painterResource(id = R.drawable.ic_x), contentDescription = "X")
        } else if (cellValue == "O") {
            Image(painter = painterResource(id = R.drawable.ic_o), contentDescription = "O")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGameBoard() {
    TicTacToeTheme {
        GameState()
    }
}
