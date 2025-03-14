package com.example.a3activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class TravelActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                TravelScreen()

        }
    }
}
@Composable
fun TravelScreen() {
    val descriptions = listOf(
        "Machu Picchu este un oras-templu incas din secolul al XV-lea d.Hr.",
        "Ibiza este o insula din arhipelagul Baleare.",
        "Cascada Niagara este un ansamblu format din trei cadere de apa.",
        "Big Ben este cel mai mare ceas cu clopot.",
        "Vulcanii Noroiosi de la Paclele Mici."
    )

    val images = listOf(
        R.drawable.machupichu,
        R.drawable.ibiza,
        R.drawable.cascada,
        R.drawable.bigben,
        R.drawable.vulcani
    )

    var currentIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = images[currentIndex]),
            contentDescription = "Description Image",
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = descriptions[currentIndex],
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (currentIndex < descriptions.size - 1) {
                    currentIndex++
                } else {
                    currentIndex = 0
                }
            }
        ) {
            Text("Next")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val intent = Intent(context, QuizActivity::class.java)
                context.startActivity(intent)
            }
        ) {
            Text("Mergi la Quiz")
        }
    }
}
