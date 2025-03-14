package com.example.contact
import android.net.Uri
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contact.ui.theme.ContactTheme
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

val contactList = mutableStateListOf<Contact>()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (contactList.isEmpty()) {
            contactList.addAll(listOf(
                Contact("Mircea", "0774677395", "Tarnaveni", "mircea_kis@gmail.com", "linkedin.com/mirceakis"),
            ))
        }
        setContent {
            ContactTheme {
                Greeting()
            }
        }
    }
}
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        Text(
            text = "Phone book",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 24.sp,
                color = Color.Black
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(contactList) { contact ->
                ContactItem(contact = contact)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(6.dp)
                .height(48.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, AddContactActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(horizontal = 8.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Add Contact", color = Color.Black)
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = contact.name,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${contact.phone}")
            }
            context.startActivity(intent)
        }) {
            Icon(Icons.Filled.Phone, contentDescription = "Phone Call", tint = Color.Black)
        }
        IconButton(onClick = {
            val intent = Intent(context, AddContactActivity::class.java).apply {
                putExtra("contact", contact)
            }
            context.startActivity(intent)
        }) {
            Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Color.Black)
        }

    }
}
