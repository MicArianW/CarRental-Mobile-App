package com.example.carrental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.carrental.ui.theme.CarRentalTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarRentalTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    CarRentalApp()
                }
            }
        }
    }
}

@Composable
fun CarRentalApp() {
    val navController = rememberNavController()
    val cars = getSampleCars()
    val bookingViewModel: BookingViewModel = viewModel()
    
    // Track if user is logged in (demo mode - always starts logged out)
    var isLoggedIn by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "carList" else "login"
    ) {

        // --------------- Login Screen -----------------
        composable("login") {
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    isLoggedIn = true
                    navController.navigate("carList") {
                        // Clear back stack so user can't go back to login
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // --------------- Register Screen -----------------
        composable("register") {
            RegisterScreen(
                navController = navController,
                onRegisterSuccess = {
                    isLoggedIn = true
                    navController.navigate("carList") {
                        // Clear back stack
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // --------------- Car List Screen -----------------
        composable("carList") {
            CarListingScreen(
                navController = navController,
                cars = cars,
                viewModel = bookingViewModel,
                onCarClick = { car ->
                    navController.navigate("carDetails/${car.id}")
                },
                onLogout = {
                    isLoggedIn = false
                    navController.navigate("login") {
                        popUpTo("carList") { inclusive = true }
                    }
                }
            )
        }

        // --------------- Car Details Screen -----------------
        composable(
            "carDetails/{carId}",
            arguments = listOf(navArgument("carId") { type = NavType.StringType })
        ) { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId") ?: return@composable
            val car = getSampleCarById(carId) ?: return@composable

            CarDetailsScreen(
                car = car,
                navController = navController
            )
        }

        // --------------- Checkout Screen -----------------
        composable(
            "checkout/{carId}/{start}/{end}/{days}",
            arguments = listOf(
                navArgument("carId") { type = NavType.StringType },
                navArgument("start") { type = NavType.StringType },
                navArgument("end") { type = NavType.StringType },
                navArgument("days") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val carId = backStackEntry.arguments?.getString("carId")!!
            val start = backStackEntry.arguments?.getString("start")!!
            val end = backStackEntry.arguments?.getString("end")!!
            val days = backStackEntry.arguments?.getInt("days")!!

            val car = getSampleCarById(carId)
            car?.let {
                CheckoutScreen(
                    navController = navController,
                    car = it,
                    startDate = start,
                    endDate = end,
                    days = days,
                    viewModel = bookingViewModel
                )
            }
        }

        // --------------- Confirmation Screen -----------------
        composable(
            "confirmation/{carId}/{start}/{end}/{days}/{total}",
            arguments = listOf(
                navArgument("carId") { type = NavType.StringType },
                navArgument("start") { type = NavType.StringType },
                navArgument("end") { type = NavType.StringType },
                navArgument("days") { type = NavType.IntType },
                navArgument("total") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")!!
            val start = backStackEntry.arguments?.getString("start")!!
            val end = backStackEntry.arguments?.getString("end")!!
            val days = backStackEntry.arguments?.getInt("days")!!
            val total = backStackEntry.arguments?.getString("total")?.toDoubleOrNull() ?: 0.0

            val car = getSampleCarById(carId)
            car?.let {
                ConfirmationScreen(
                    navController = navController,
                    car = it,
                    startDate = start,
                    endDate = end,
                    days = days,
                    total = total
                )
            }
        }

        // --------------- My Bookings Screen -----------------
        composable("myBookings") {
            MyBookingsScreen(
                navController = navController,
                viewModel = bookingViewModel
            )
        }

        // --------------- Manage Bookings Screen -----------------
        composable("manageBookings") {
            ManageBookingsScreen(
                navController = navController,
                viewModel = bookingViewModel
            )
        }
    }
}
