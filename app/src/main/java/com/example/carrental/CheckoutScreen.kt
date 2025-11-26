package com.example.carrental

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CheckoutScreen(
    navController: NavController,
    car: Car,
    startDate: String,
    endDate: String,
    days: Int,
    viewModel: BookingViewModel
) {

    val context = LocalContext.current

    // ------- price calculation -------
    val pricePerDay = car.pricePerDay
        .replace("CA$", "")
        .replace("$", "")
        .replace("/day", "")
        .trim()
        .toDouble()
    val subtotal = pricePerDay * days
    val tax = subtotal * 0.13
    val total = subtotal + tax

    // ------- primary driver & payment form state -------
    var countryCode by remember { mutableStateOf("CA +1") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiry by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    var billingCountry by remember { mutableStateOf("Canada") }
    var marketingOptIn by remember { mutableStateOf(false) }
    var agreeTerms by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text("Checkout", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))

        // =====================================================
        // PRIMARY DRIVER
        // =====================================================
        Text("Primary driver", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        Row(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = countryCode,
                onValueChange = { countryCode = it },
                label = { Text("Country") },
                modifier = Modifier
                    .weight(0.5f)
                    .padding(end = 4.dp)
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Mobile number") },
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 4.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        // =====================================================
        // PAYMENT METHOD (CUSTOM UI)
        // =====================================================
        Text("Payment method", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))

        Text("Your information will be stored securely.", fontSize = 12.sp, color = Color.Gray)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Card number") },
            placeholder = { Text("1234 1234 1234 1234") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Row(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = expiry,
                onValueChange = { expiry = it },
                label = { Text("Expiration date") },
                placeholder = { Text("MM / YY") },
                modifier = Modifier
                    .weight(0.5f)
                    .padding(end = 4.dp)
            )

            OutlinedTextField(
                value = cvc,
                onValueChange = { cvc = it },
                label = { Text("Security code") },
                placeholder = { Text("CVC") },
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 4.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = billingCountry,
            onValueChange = { billingCountry = it },
            label = { Text("Country") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        // =====================================================
        // SUMMARY CARD
        // =====================================================
        SummaryCard(
            car = car,
            startDate = startDate,
            endDate = endDate,
            subtotal = subtotal,
            tax = tax,
            total = total
        )

        Spacer(Modifier.height(20.dp))

        // =====================================================
        // CHECKBOXES
        // =====================================================

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = marketingOptIn,
                    onValueChange = { marketingOptIn = it }
                )
        ) {
            Checkbox(checked = marketingOptIn, onCheckedChange = null)
            Spacer(Modifier.width(4.dp))
            Text("Send me promotions and announcements via email", fontSize = 13.sp)
        }

        Spacer(Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = agreeTerms,
                    onValueChange = { agreeTerms = it }
                )
        ) {
            Checkbox(checked = agreeTerms, onCheckedChange = null)
            Spacer(Modifier.width(4.dp))
            Text(
                "I agree to pay the total shown and accept the terms.",
                fontSize = 13.sp
            )
        }

        Spacer(Modifier.height(24.dp))

        // =====================================================
        // BOOK TRIP BUTTON
        // =====================================================
        Button(
            onClick = {

                if (cardNumber.length < 12 || expiry.length < 4 || cvc.length < 3) {
                    Toast.makeText(context, "Please complete payment details.", Toast.LENGTH_SHORT)
                        .show()
                    return@Button
                }

                if (!agreeTerms) {
                    Toast.makeText(context, "You must accept the terms.", Toast.LENGTH_SHORT)
                        .show()
                    return@Button
                }

                // Create booking in database
                viewModel.createBooking(
                    car = car,
                    startDate = startDate,
                    endDate = endDate,
                    days = days,
                    totalCost = total
                ) {
                    // Navigate to confirmation screen
                    navController.navigate(
                        "confirmation/${car.id}/$startDate/$endDate/$days/${total}"
                    )
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text("Book trip", fontSize = 18.sp)
        }

        Spacer(Modifier.height(40.dp))
    }
}

// -------------------- SUMMARY CARD --------------------
@Composable
private fun SummaryCard(
    car: Car,
    startDate: String,
    endDate: String,
    subtotal: Double,
    tax: Double,
    total: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color(0xFFF7F7F7)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            Text(car.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Trip Summary", fontSize = 14.sp, color = Color.Gray)

            Spacer(Modifier.height(12.dp))

            Text("Start: $startDate at 10:00 AM")
            Text("End:   $endDate at 10:00 AM")
            Spacer(Modifier.height(6.dp))
            Text("Pickup: Toronto M5V 2T6")

            Spacer(Modifier.height(16.dp))
            Divider()
            Spacer(Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Subtotal")
                Text("CA$${String.format("%.2f", subtotal)}")
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Sales tax (13%)")
                Text("CA$${String.format("%.2f", tax)}")
            }

            Spacer(Modifier.height(10.dp))
            Divider()
            Spacer(Modifier.height(10.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Trip total", fontWeight = FontWeight.Bold)
                Text("CA$${String.format("%.2f", total)}", fontWeight = FontWeight.Bold)
            }
        }
    }
}
