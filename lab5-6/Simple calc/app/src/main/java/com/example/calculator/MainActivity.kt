package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.collection.emptyLongSet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Calc()
            }
        }
    }
}
@Preview
@Composable
fun Calc( modifier: Modifier = Modifier) {
    var textState by remember { mutableStateOf("")};
    var firstNumber by remember { mutableStateOf("")}
    var secondNumber by remember { mutableStateOf("")}
    var result by remember { mutableStateOf(0f) }



    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = firstNumber,
            onValueChange = {
                 firstNumber = it

            },
        )
        OutlinedTextField(
            value = secondNumber,
            onValueChange = {
                secondNumber=it
            },
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            result=firstNumber.toFloat();
            result+= secondNumber.toFloat();
            textState=result.toString();

        }) {
            Text(text = "Add")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            result=firstNumber.toFloat();
            result-= secondNumber.toFloat();
            textState=result.toString();

        }) {
            Text(text = "Sub")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (secondNumber == "0"){
                textState="Can not div by 0";
            }
            
            else{
                result=firstNumber.toFloat();
                result/= secondNumber.toFloat();
                textState=result.toString();
            }


        }) {
            Text(text = "Div")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            result=firstNumber.toFloat();
            result*= secondNumber.toFloat();
            textState=result.toString();


        }) {
            Text(text = "Mul")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = textState,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

}

@Composable
fun GreetingPreview() {
    CalculatorTheme {
       Calc()
    }
}}