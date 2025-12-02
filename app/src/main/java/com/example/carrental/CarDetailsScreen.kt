package com.example.carrental

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.*
import java.util.Calendar

@Composable
fun CarDetailsScreen(
    car: Car,
    navController: NavController
) {

    val context = LocalContext.current

    // Dates
    val startCal = remember { Calendar.getInstance() }
    val endCal = remember { Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) } }

    var startDate by remember { mutableStateOf(formatDate(startCal)) }
    var endDate by remember { mutableStateOf(formatDate(endCal)) }

    var pickupLocation by remember { mutableStateOf("Toronto, ON M5V 2T6") }

    // Coordinates (Toronto — replace with real if needed)
    val pickupLatLng = LatLng(43.6532, -79.3832)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(pickupLatLng, 14f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .verticalScroll(rememberScrollState())
    ) {

        Image(
            painter = painterResource(car.imageRes),
            contentDescription = car.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(12.dp))

        LazyRow(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            items(3) {
                Card(
                    modifier = Modifier
                        .size(120.dp, 80.dp)
                        .padding(end = 8.dp)
                ) {
                    Image(
                        painter = painterResource(car.imageRes),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Column(Modifier.padding(horizontal = 16.dp)) {

            Text(car.name, fontWeight = FontWeight.Bold, fontSize = 26.sp)
            Text("4.9 ★ (86 trips) • All-Star Host", fontSize = 14.sp)

            Spacer(Modifier.height(12.dp))

            Row {
                InfoChip("${car.seats} seats")
                Spacer(Modifier.width(8.dp))
                InfoChip(car.fuelType)
                Spacer(Modifier.width(8.dp))
                InfoChip(car.transmission)
            }

            Spacer(Modifier.height(16.dp))

            Text("Hosted by", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(48.dp)
                        .background(Color.Gray)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Enterprise", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("10,000+ trips • Joined Jan 2010", fontSize = 13.sp, color = Color.Gray)
                }
            }

            Spacer(Modifier.height(24.dp))
            Divider()
            Spacer(Modifier.height(16.dp))

            Text("Vehicle features", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))

            FeatureSection()

            Spacer(Modifier.height(24.dp))
            Divider()
            Spacer(Modifier.height(16.dp))

            Text("Your trip", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))

            Text("Trip start")
            DateField(startDate) {
                openCalendar(context, startCal) {
                    startDate = formatDate(startCal)
                }
            }

            Spacer(Modifier.height(12.dp))

            Text("Trip end")
            DateField(endDate) {
                openCalendar(context, endCal) {
                    if (endCal.timeInMillis <= startCal.timeInMillis) {
                        Toast.makeText(
                            context,
                            "End date must be after start date",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        endDate = formatDate(endCal)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Divider()
            Spacer(Modifier.height(16.dp))

            // PICKUP SECTION (CLICKABLE + MAP)
            Text("Pickup & return location")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clickable {
                        openInGoogleMaps(context, pickupLocation)
                    }
            ) {
                Text(pickupLocation, fontSize = 16.sp, color = Color(0xFF1A73E8))
                Spacer(Modifier.weight(1f))
                Icon(Icons.Default.Edit, contentDescription = "")
            }

            Spacer(Modifier.height(16.dp))

            // -------- EMBEDDED GOOGLE MAP BELOW PICKUP ----------
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = MarkerState(position = pickupLatLng),
                        title = "Pickup Location",
                        snippet = pickupLocation
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
            Divider()
            Spacer(Modifier.height(16.dp))

            // CONTINUE BUTTON
            Button(
                onClick = {
                    val days =
                        ((endCal.timeInMillis - startCal.timeInMillis) / 86400000).toInt()
                    navController.navigate("checkout/${car.id}/$startDate/$endDate/$days")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Continue", fontSize = 18.sp)
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

// ----- Helper UI components (unchanged) -----

fun formatDate(cal: Calendar): String =
    "%04d-%02d-%02d".format(
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH) + 1,
        cal.get(Calendar.DAY_OF_MONTH)
    )

fun openCalendar(context: Context, cal: Calendar, onSelected: () -> Unit) {
    DatePickerDialog(
        context,
        { _, y, m, d ->
            cal.set(y, m, d)
            onSelected()
        },
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH),
        cal.get(Calendar.DAY_OF_MONTH)
    ).show()
}

fun openInGoogleMaps(context: Context, address: String) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(address))
    )
    intent.setPackage("com.google.android.apps.maps")
    context.startActivity(intent)
}

@Composable
fun FeatureSection() {
    Text("Safety", fontWeight = FontWeight.Bold)
    FeatureText("Adaptive cruise control")
    FeatureText("Blind spot warning")
    FeatureText("All-wheel drive")
    FeatureText("Brake assist")
    FeatureText("Lane departure warning")

    Spacer(Modifier.height(12.dp))

    Text("Connectivity", fontWeight = FontWeight.Bold)
    FeatureText("Apple CarPlay")
    FeatureText("Android Auto")
    FeatureText("Bluetooth")
    FeatureText("USB charger")

    Spacer(Modifier.height(12.dp))

    Text("Convenience", fontWeight = FontWeight.Bold)
    FeatureText("Keyless entry")
    FeatureText("Heated seats")
}

@Composable
fun FeatureText(text: String) {
    Text(text, fontSize = 15.sp, color = Color.DarkGray)
}

@Composable
fun InfoChip(text: String) {
    Card(colors = CardDefaults.cardColors(Color(0xFFF0F0F0))) {
        Text(text, modifier = Modifier.padding(8.dp), fontSize = 13.sp)
    }
}

@Composable
fun DateField(label: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(Color(0xFFF3F3F3))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, fontSize = 16.sp)
            Spacer(Modifier.weight(1f))
            Text("▾", fontSize = 20.sp)
        }
    }
}




