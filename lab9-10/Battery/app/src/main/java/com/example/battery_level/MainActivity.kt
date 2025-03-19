package com.example.battery_level

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
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
import androidx.core.app.NotificationCompat
import com.example.battery_level.ui.theme.BatteryLevelTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        createNotificationChannel()

        setContent {
            BatteryLevelTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BatteryLevelScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Battery Level Notifications"
            val descriptionText = "Notificari pentru nivelul bateriei"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("battery_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

@Composable
fun BatteryLevelScreen(modifier: Modifier = Modifier) {
    var batteryLevel by remember { mutableStateOf(0) }

    val context = LocalContext.current

    val batteryReceiver = remember {
        BatteryReceiver { level ->
            batteryLevel = level

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val notification: Notification = NotificationCompat.Builder(context, "battery_channel")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Nivel baterie")
                .setContentText("Bateria este la $batteryLevel%")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(1, notification)
        }
    }

    DisposableEffect(context) {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(batteryReceiver, filter)

        onDispose {
            context.unregisterReceiver(batteryReceiver)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Nivel baterie: $batteryLevel%", style = MaterialTheme.typography.headlineMedium)
    }
}

class BatteryReceiver(private val updateBatteryLevel: (Int) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        when (intent.action) {
            Intent.ACTION_BATTERY_CHANGED -> {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                updateBatteryLevel(level)

                val notification: Notification = NotificationCompat.Builder(context, "battery_channel")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Nivel baterie")
                    .setContentText("Bateria este la $level%")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .build()

                notificationManager.notify(1, notification)
            }

            Intent.ACTION_BATTERY_LOW -> {
                val notification: Notification = NotificationCompat.Builder(context, "battery_channel")
                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                    .setContentTitle("Baterie scazuta")
                    .setContentText("Baterie scazuta! Conecteaza incarcatorul.")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .build()

                notificationManager.notify(2, notification)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BatteryLevelPreview() {
    BatteryLevelTheme {
        BatteryLevelScreen()
    }
}
