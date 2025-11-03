package com.example.carrental

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun CarListingScreen(
    onCarClick: (String) -> Unit,
    onProfileClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Available Cars") },
                actions = {
                    TextButton(onClick = { onProfileClick() }) {
                        Text("Profile")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Tap a car to view details:",
                style = MaterialTheme.typography.titleMedium
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCarClick("car1") }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Toyota Corolla 2021", style = MaterialTheme.typography.titleMedium)
                    Text("$45/day", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCarClick("car2") }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Honda Civic 2022", style = MaterialTheme.typography.titleMedium)
                    Text("$55/day", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}


