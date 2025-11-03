package com.example.carrental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carrental.ui.theme.CarRentalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarRentalTheme {
                CarRentalApp()
            }
        }
    }
}

@Composable
fun CarRentalApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("carListing") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("carListing") },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable("carListing") {
            CarListingScreen(
                onCarClick = { carId -> navController.navigate("carDetails/$carId") },
                onProfileClick = { navController.navigate("profile") }
            )
        }
        composable("carDetails/{carId}") { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId") ?: ""
            CarDetailsScreen(
                carId = carId,
                onBookClick = { navController.navigate("booking/$carId") },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("booking/{carId}") { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId") ?: ""
            BookingScreen(
                carId = carId,
                onBookingComplete = { navController.navigate("carListing") },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("profile") {
            ProfileScreen(
                onBackClick = { navController.popBackStack() },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}


