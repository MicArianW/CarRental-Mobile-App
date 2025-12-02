package com.example.carrental

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// -----------------------------------------------------------
// ConfirmationScreen
// Shown AFTER the user completes a booking.
// Displays success icon, trip summary, car details and payment summary.
// -----------------------------------------------------------

@Composable
fun ConfirmationScreen(
    navController: NavController,
    car: Car,
    startDate: String,
    endDate: String,
    days: Int,
    total: Double
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .verticalScroll(rememberScrollState()) // Allows scrolling
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Success Icon + Title
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Success",
            tint = Color(0xFF4CAF50),
            modifier = Modifier.size(80.dp)
        )

        Text(
            text = "Booking Confirmed!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        // ------------------------------
        // Car Details Card
        // Shows car image, name, trip info, specs and payment summary
        // ------------------------------
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(Color.White),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                // Car image
                Image(
                    painter = painterResource(id = car.imageRes),
                    contentDescription = car.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                // Trip details
                DetailRow("Pickup Date", "$startDate at 10:00 AM")
                DetailRow("Return Date", "$endDate at 10:00 AM")
                DetailRow("Duration", "$days days")

                // Payment calculation (based on car.pricePerDay)
                val pricePerDay = car.pricePerDay
                    .replace("CA$", "")
                    .replace("$", "")
                    .replace("/day", "")
                    .trim()
                    .toDouble()

                val subtotal = pricePerDay * days
                val tax = subtotal * 0.13

                // Total Paid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Paid", fontWeight = FontWeight.Bold)
                    Text("CA$${String.format("%.2f", total)}", fontWeight = FontWeight.Bold)
                }
            }
        }

        // Back To Home Button
        Button(
            onClick = {
                navController.navigate("carList") {
                    popUpTo("carList") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }
}

// -----------------------------------------------------------
// Reusable row component for details labels + values
// -----------------------------------------------------------
@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Medium)
    }
}

