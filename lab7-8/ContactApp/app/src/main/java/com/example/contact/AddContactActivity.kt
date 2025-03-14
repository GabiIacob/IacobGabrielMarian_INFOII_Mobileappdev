    package com.example.contact

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
    import com.example.contact.ui.theme.ContactTheme

    class AddContactActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            val contact = intent?.getParcelableExtra<Contact>("contact")
            setContent {
                ContactTheme {
                    AddContact(contact)
                }
            }
        }
    }
    @Composable
    fun AddContact(contact: Contact?) {
        var contactName by remember { mutableStateOf(contact?.name ?: "") }
        var contactPhone by remember { mutableStateOf(contact?.phone ?: "") }
        var contactAddress by remember { mutableStateOf(contact?.address ?: "") }
        var contactEmail by remember { mutableStateOf(contact?.email ?: "") }
        var contactLinkedIn by remember { mutableStateOf(contact?.linkedIn ?: "") }

        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (contact == null) "Add Contact" else "Edit Contact",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = contactName,
                onValueChange = { contactName = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = contactPhone,
                onValueChange = { contactPhone = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = contactAddress,
                onValueChange = { contactAddress = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = contactEmail,
                onValueChange = { contactEmail = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = contactLinkedIn,
                onValueChange = { contactLinkedIn = it },
                label = { Text("LinkedIn") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            Button(
                onClick = {
                    val updatedContact = Contact(
                        name = contactName,
                        phone = contactPhone,
                        address = contactAddress,
                        email = contactEmail,
                        linkedIn = contactLinkedIn
                    )
                    if (contact != null) {
                        val index = contactList.indexOfFirst { it.name == contact.name && it.phone == contact.phone }
                        if (index != -1) {
                            contactList[index] = updatedContact
                        }
                    } else {
                        contactList.add(updatedContact)
                    }

                    (context as? Activity)?.finish()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(if (contact == null) "Save Contact" else "Update Contact")
            }
        }
    }
