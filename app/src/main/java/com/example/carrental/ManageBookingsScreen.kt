package com.example.carrental

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageBookingsScreen(
    navController: NavController,
    viewModel: BookingViewModel
) {
    val bookings by viewModel.bookings.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var bookingToDelete by remember { mutableStateOf<Booking?>(null) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Bookings") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        if (bookings.isEmpty()) {
            // Empty State
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🚗",
                    fontSize = 64.sp
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "No Bookings to Manage",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "You don't have any bookings to cancel.",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF8F8F8))
                    .padding(paddingValues)
            ) {
                // Info Banner
                Surface(
                    color = Color(0xFFFFF3E0),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "⚠️",
                            fontSize = 24.sp
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = "Tap on a booking to cancel it. The car will become available again.",
                            fontSize = 14.sp,
                            color = Color(0xFF424242)
                        )
                    }
                }
                
                // Bookings List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            text = "Your Bookings",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF212121)
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    
                    items(bookings) { booking ->
                        CancellableBookingCard(
                            booking = booking,
                            onCancelClick = {
                                bookingToDelete = booking
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }
    }
    
    // Delete Confirmation Dialog
    if (showDeleteDialog && bookingToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Cancel Booking?") },
            text = {
                Text("Are you sure you want to cancel your booking for ${bookingToDelete?.carName}? This action cannot be undone.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        bookingToDelete?.let { booking ->
                            viewModel.cancelBooking(booking) {
                                showDeleteDialog = false
                                bookingToDelete = null
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F)
                    )
                ) {
                    Text("Cancel Booking")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Keep Booking")
                }
            }
        )
    }
}

@Composable
private fun CancellableBookingCard(
    booking: Booking,
    onCancelClick: () -> Unit
) {
    val car = getSampleCarById(booking.carId)
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Car Image
                car?.let {
                    Image(
                        painter = painterResource(id = it.imageRes),
                        contentDescription = booking.carName,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                
                Spacer(Modifier.width(16.dp))
                
                // Booking Info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = booking.carName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                    
                    Spacer(Modifier.height(8.dp))
                    
                    Text(
                        text = "📅 ${booking.pickupDate}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    
                    Spacer(Modifier.height(4.dp))
                    
                    Text(
                        text = "📍 ${booking.pickupLocation}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    
                    Spacer(Modifier.height(8.dp))
                    
                    Text(
                        text = "CA$${booking.totalCost}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                }
            }
            
            Spacer(Modifier.height(16.dp))
            
            // Cancel Button
            Button(
                onClick = onCancelClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Cancel This Booking", fontSize = 16.sp)
            }
        }
    }
}
