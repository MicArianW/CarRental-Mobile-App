package com.example.carrental

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.Cancel
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
fun CarListingScreen(
    navController: NavController,
    cars: List<Car>,
    viewModel: BookingViewModel,
    onCarClick: (Car) -> Unit = {},
    onLogout: () -> Unit = {}
) {
    // Collect booked car IDs from ViewModel
    val bookedCarIds by viewModel.bookedCarIds.collectAsState()
    
    // Filter out booked cars
    val availableCars = cars.filter { car ->
        car.id !in bookedCarIds
    }

    // --- FILTER STATES ---
    var selectedVehicleType by remember { mutableStateOf<VehicleType?>(null) }
    var selectedMakes by remember { mutableStateOf(setOf<String>()) }
    var priceRange by remember { mutableStateOf(0f..200f) }
    var minSeats by remember { mutableStateOf<Int?>(null) }
    var minYear by remember { mutableStateOf(2018) }
    var electricOnly by remember { mutableStateOf(false) }

    // --- SHEET STATE ---
    var activeSheet by remember { mutableStateOf<FilterSheet?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    
    // User menu state
    var showUserMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {

        // Enhanced Search Bar with User Icon
        EnhancedSearchBar(
            onUserIconClick = { showUserMenu = true }
        )

        TuroFilterBar(
            onMakeModelClick = { activeSheet = FilterSheet.MAKE_MODEL },
            onPriceClick = { activeSheet = FilterSheet.PRICE },
            onYearClick = { activeSheet = FilterSheet.YEAR },
            onSeatsClick = { activeSheet = FilterSheet.SEATS },
            onElectricClick = { activeSheet = FilterSheet.ELECTRIC },
            onAllFiltersClick = { activeSheet = FilterSheet.ALL }
        )

        Spacer(Modifier.height(8.dp))

        // =======================================================
        //  FILTERING LOGIC
        // =======================================================
        val filteredCars = availableCars.filter { car ->

            // --- Normalize make ---
            val make = car.name.substringBefore(" ").trim().lowercase()
            val selectedMakesNormalized =
                selectedMakes.map { it.trim().lowercase() }.toSet()

            val makeOk =
                selectedMakesNormalized.isEmpty() || selectedMakesNormalized.contains(make)

            // --- Price ---
            val price = car.pricePerDay.filter(Char::isDigit).toIntOrNull() ?: 9999
            val priceOk = price in priceRange.start.toInt()..priceRange.endInclusive.toInt()

            // --- Year ---
            val yearMatches = Regex("\\d{4}")
                .findAll(car.name)
                .map { it.value.toInt() }
                .toList()
            val year = yearMatches.lastOrNull() ?: 0
            val yearOk = year >= minYear

            // --- Seats ---
            val seatsOk = minSeats?.let { car.seats >= it } ?: true

            // --- Vehicle Type ---
            val typeOk = selectedVehicleType?.let { car.vehicleType == it } ?: true

            // --- Electric ---
            val electricOk = if (!electricOnly) true else car.fuelType.equals("Electric", true)

            priceOk && yearOk && seatsOk && typeOk && electricOk && makeOk
        }

        // =======================================================
        //  CAR LIST
        // =======================================================
        if (filteredCars.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🚗",
                    fontSize = 64.sp
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "No Cars Available",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "All cars are currently booked or don't match your filters.",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
            ) {
                items(filteredCars) { car ->
                    CarItem(
                        car = car,
                        onClick = { onCarClick(car) }
                    )
                }
            }
        }
    }

    // User Menu Dropdown
    if (showUserMenu) {
        UserMenuDropdown(
            onDismiss = { showUserMenu = false },
            onViewBookings = {
                showUserMenu = false
                navController.navigate("myBookings")
            },
            onManageBookings = {
                showUserMenu = false
                navController.navigate("manageBookings")
            }
        )
    }

    // Modal Filter Sheets
    if (activeSheet != null) {
        ModalBottomSheet(
            onDismissRequest = { activeSheet = null },
            sheetState = sheetState
        ) {

            when (activeSheet) {

                FilterSheet.MAKE_MODEL -> MakeModelSheet(
                    cars = cars,
                    selectedMakes = selectedMakes,
                    onApply = {
                        selectedMakes = it
                        activeSheet = null
                    },
                    onClose = { activeSheet = null }
                )

                FilterSheet.PRICE -> PriceSheet(
                    currentRange = priceRange,
                    onApply = {
                        priceRange = it
                        activeSheet = null
                    },
                    onClose = { activeSheet = null }
                )

                FilterSheet.YEAR -> YearSheet(
                    currentMinYear = minYear,
                    onApply = {
                        minYear = it
                        activeSheet = null
                    },
                    onClose = { activeSheet = null }
                )

                FilterSheet.SEATS -> SeatsSheet(
                    currentMinSeats = minSeats,
                    onApply = {
                        minSeats = it
                        activeSheet = null
                    },
                    onClose = { activeSheet = null }
                )

                FilterSheet.ELECTRIC -> ElectricSheet(
                    electricOnly = electricOnly,
                    onApply = {
                        electricOnly = it
                        activeSheet = null
                    },
                    onClose = { activeSheet = null }
                )

                FilterSheet.ALL -> AllFiltersSheet(
                    selectedVehicleType = selectedVehicleType,
                    onVehicleTypeChange = { selectedVehicleType = it },
                    onReset = {
                        selectedVehicleType = null
                        selectedMakes = emptySet()
                        priceRange = 0f..200f
                        minSeats = null
                        minYear = 2018
                        electricOnly = false
                        activeSheet = null
                    },
                    onClose = { activeSheet = null }
                )

                null -> {} // required
            }
        }
    }
}

// ============================================================
//  Enhanced Search Bar with User Icon
// ============================================================
@Composable
fun EnhancedSearchBar(onUserIconClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F3F3))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Anywhere", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Any date – Any date", color = Color.Gray)
            }
            
            // User Icon Button
            IconButton(
                onClick = onUserIconClick,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF6200EE))
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Menu",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

// ============================================================
//  User Menu Dropdown
// ============================================================
@Composable
fun UserMenuDropdown(
    onDismiss: () -> Unit,
    onViewBookings: () -> Unit,
    onManageBookings: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "My Account",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // View Bookings Option
                Card(
                    onClick = onViewBookings,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF3E5F5)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.BookOnline,
                            contentDescription = null,
                            tint = Color(0xFF6200EE),
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(
                                "Current Bookings",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF212121)
                            )
                            Text(
                                "View your active trips",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
                
                // Cancel Bookings Option
                Card(
                    onClick = onManageBookings,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFEBEE)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = null,
                            tint = Color(0xFFD32F2F),
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(
                                "Manage Bookings",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF212121)
                            )
                            Text(
                                "Cancel existing bookings",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

// ============================================================
//  Reuse existing filter components
// ============================================================
@Composable
fun TuroFilterBar(
    onMakeModelClick: () -> Unit,
    onPriceClick: () -> Unit,
    onYearClick: () -> Unit,
    onSeatsClick: () -> Unit,
    onElectricClick: () -> Unit,
    onAllFiltersClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        TuroFilterChip("Make & model", onClick = onMakeModelClick)
        TuroFilterChip("Price", onClick = onPriceClick)
        TuroFilterChip("Years", onClick = onYearClick)
        TuroFilterChip("Seats", onClick = onSeatsClick)
        TuroFilterChip("Electric", onClick = onElectricClick)
        TuroFilterChip("All filters", onClick = onAllFiltersClick, isPrimary = true)
    }
}

@Composable
fun TuroFilterChip(
    text: String,
    isPrimary: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (isPrimary) Color.Black else Color.White,
            contentColor = if (isPrimary) Color.White else Color.Black
        ),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            fontSize = 14.sp
        )
    }
}

// ============================================================
//  Car Item Card
// ============================================================
@Composable
fun CarItem(car: Car, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = car.imageRes),
                contentDescription = car.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(Modifier.padding(16.dp)) {
                Text(
                    car.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("⛽ ${car.fuelType}", fontSize = 14.sp, color = Color.Gray)
                    Text("👥 ${car.seats} seats", fontSize = 14.sp, color = Color.Gray)
                    Text("🔧 ${car.transmission}", fontSize = 14.sp, color = Color.Gray)
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        car.pricePerDay,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6200EE)
                    )

                    Button(
                        onClick = onClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6200EE)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("View Details")
                    }
                }
            }
        }
    }
}
