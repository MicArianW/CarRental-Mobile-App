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
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))
        
        // Success Icon
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Success",
            tint = Color(0xFF4CAF50),
            modifier = Modifier.size(80.dp)
        )
        
        Spacer(Modifier.height(16.dp))
        
        Text(
            text = "Booking Confirmed!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121)
        )
        
        Spacer(Modifier.height(8.dp))
        
        Text(
            text = "Your trip has been successfully booked",
            fontSize = 16.sp,
            color = Color.Gray
        )
        
        Spacer(Modifier.height(32.dp))
        
        // Car Details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Car Image
                Image(
                    painter = painterResource(id = car.imageRes),
                    contentDescription = car.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                
                Spacer(Modifier.height(16.dp))
                
                // Car Name
                Text(
                    text = car.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                
                Spacer(Modifier.height(16.dp))
                
                // Trip Details Section
                Text(
                    text = "Trip Details",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF424242)
                )
                
                Spacer(Modifier.height(12.dp))
                
                DetailRow(label = "Pickup Date", value = "$startDate at 10:00 AM")
                DetailRow(label = "Return Date", value = "$endDate at 10:00 AM")
                DetailRow(label = "Duration", value = "$days days")
                DetailRow(label = "Pickup Location", value = "Toronto M5V 2T6")
                DetailRow(label = "Drop-off Location", value = "Toronto M5V 2T6")
                
                Spacer(Modifier.height(16.dp))
                Divider()
                Spacer(Modifier.height(16.dp))
                
                // Vehicle Specifications
                Text(
                    text = "Vehicle Specifications",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF424242)
                )
                
                Spacer(Modifier.height(12.dp))
                
                DetailRow(label = "Seats", value = "${car.seats} passengers")
                DetailRow(label = "Transmission", value = car.transmission)
                DetailRow(label = "Fuel Type", value = car.fuelType)
                DetailRow(label = "Mileage", value = car.km)
                
                Spacer(Modifier.height(16.dp))
                Divider()
                Spacer(Modifier.height(16.dp))
                
                // Pricing Summary
                Text(
                    text = "Payment Summary",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF424242)
                )
                
                Spacer(Modifier.height(12.dp))
                
                val pricePerDay = car.pricePerDay
                    .replace("CA$", "")
                    .replace("$", "")
                    .replace("/day", "")
                    .trim()
                    .toDouble()
                val subtotal = pricePerDay * days
                val tax = subtotal * 0.13
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Daily Rate", color = Color.Gray)
                    Text("CA$${String.format("%.2f", pricePerDay)}", color = Color.Gray)
                }
                
                Spacer(Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Subtotal ($days days)", color = Color.Gray)
                    Text("CA$${String.format("%.2f", subtotal)}", color = Color.Gray)
                }
                
                Spacer(Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Sales Tax (13%)", color = Color.Gray)
                    Text("CA$${String.format("%.2f", tax)}", color = Color.Gray)
                }
                
                Spacer(Modifier.height(12.dp))
                Divider()
                Spacer(Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Total Paid",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                    Text(
                        "CA$${String.format("%.2f", total)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                }
            }
        }
        
        Spacer(Modifier.height(24.dp))
        
        // Information Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(Color(0xFFE3F2FD)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "📧 Confirmation Email Sent",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1976D2)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "A confirmation email with your booking details has been sent to your email address.",
                    fontSize = 14.sp,
                    color = Color(0xFF424242)
                )
            }
        }
        
        Spacer(Modifier.height(24.dp))
        
        // Back to Home Button
        Button(
            onClick = {
                navController.navigate("carList") {
                    popUpTo("carList") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6200EE)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Back to Home", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
        
        Spacer(Modifier.height(40.dp))
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color(0xFF212121),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
    }
}
