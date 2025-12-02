package com.example.carrental

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

// ------------------------------------------------------------------
// BookingViewModel
// Handles:
//  - Loading all bookings from Room
//  - Creating a new booking
//  - Cancelling a booking
//  - Tracking booked cars to hide unavailable cars from listings
// ------------------------------------------------------------------

class BookingViewModel(application: Application) : AndroidViewModel(application) {

    // Build Room database instance
    private val database = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "car_rental_db"
    ).build()

    // BookingDao used for database operations
    private val bookingDao = database.bookingDao()

    // All active bookings
    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    // Stores IDs of cars that are already booked
    private val _bookedCarIds = MutableStateFlow<Set<String>>(emptySet())
    val bookedCarIds: StateFlow<Set<String>> = _bookedCarIds.asStateFlow()

    init {
        // Load bookings when ViewModel starts
        loadBookings()
    }

    // --------------------------------------------------------------
    // Loads all active bookings for this user
    // --------------------------------------------------------------
    private fun loadBookings() {
        viewModelScope.launch {
            try {
                val userId = "demo_user_123" // placeholder for logged-in user

                val bookingsList = bookingDao.getBookingsByUser(userId)

                // Keep only active bookings
                _bookings.value = bookingsList.filter { it.status == "Active" }

                // Track booked car IDs
                _bookedCarIds.value = bookingsList
                    .filter { it.status == "Active" }
                    .map { it.carId }
                    .toSet()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // --------------------------------------------------------------
    // Creates a new booking entry in Room database
    // --------------------------------------------------------------
    fun createBooking(
        car: Car,
        startDate: String,
        endDate: String,
        days: Int,
        totalCost: Double,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {

                // Generate booking object
                val booking = Booking(
                    id = UUID.randomUUID().toString(),
                    userId = "demo_user_123",
                    carId = car.id,
                    carName = car.name,
                    pickupLocation = "Toronto M5V 2T6",
                    dropoffLocation = "Toronto M5V 2T6",
                    pickupDate = startDate,
                    returnDate = endDate,
                    totalCost = totalCost.toInt(),
                    status = "Active"
                )

                // Save in DB
                bookingDao.insertBooking(booking)

                // Refresh bookings
                loadBookings()

                onSuccess()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // --------------------------------------------------------------
    // Removes a booking from the database
    // --------------------------------------------------------------
    fun cancelBooking(booking: Booking, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                bookingDao.deleteBooking(booking)
                loadBookings()
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // --------------------------------------------------------------
    // Fetch a single booking by ID
    // --------------------------------------------------------------
    fun getBookingById(bookingId: String): Booking? {
        return _bookings.value.firstOrNull { it.id == bookingId }
    }
}
