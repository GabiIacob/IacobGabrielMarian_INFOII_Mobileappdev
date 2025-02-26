package com.example.book

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.book.ui.theme.BookTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var selectedItem by remember { mutableStateOf("Home") }
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Fram, Ursul Polar",

            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp),
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(vertical = 16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(6.dp)
                .height(48.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Part1", "Part2", "Part3", "Part4").forEach { chapter ->
                Button(
                    onClick = { selectedItem = chapter },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(horizontal = 2.dp)
                ) {
                    Text(chapter)
                }
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (selectedItem) {
                "Part1" -> Part1Screen()
                "Part2" -> Part2Screen()
                "Part3" -> Part3Screen()
                "Part4" -> Part4Screen()
                else -> HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val image = painterResource(R.drawable.framursulpolar)
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ChapterScreen(
    chapterTitle: String,
    chapterText: String,
    imageResource: Int
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)
                    .height(48.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = chapterTitle,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = chapterText,
                    style = MaterialTheme.typography.bodyLarge
                )
                val image = painterResource(id = imageResource)
                Image(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(vertical = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun Part1Screen() {
    ChapterScreen(
        chapterTitle = "Capitolul 1: Capturarea lui Fram",
        chapterText = stringResource(R.string.Part1),
        imageResource = R.drawable.framcapturat
    )
}

@Composable
fun Part2Screen() {
    ChapterScreen(
        chapterTitle = "Capitolul 2: Viata in Circ",
        chapterText = stringResource(R.string.Part2),
        imageResource = R.drawable.fram
    )
}
@Composable
fun Part3Screen() {
    ChapterScreen(
        chapterTitle = "Capitolul 3: Dorinta de Libertate",
        chapterText = stringResource(R.string.Part3),
        imageResource = R.drawable.framsuparat
    )
}
@Composable
fun Part4Screen() {
    ChapterScreen(
        chapterTitle = "Capitolul 4 : Intoarcerea Acasa",
        chapterText = stringResource(R.string.Part4),
        imageResource = R.drawable.frammom
    )
}
@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    BookTheme {
        MainScreen()
    }
}
