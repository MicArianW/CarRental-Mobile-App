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

class BookingViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "car_rental_db"
    ).build()
    
    private val bookingDao = database.bookingDao()
    
    // State for all bookings
    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()
    
    // State for booked car IDs (to hide from home page)
    private val _bookedCarIds = MutableStateFlow<Set<String>>(emptySet())
    val bookedCarIds: StateFlow<Set<String>> = _bookedCarIds.asStateFlow()
    
    init {
        loadBookings()
    }
    
    private fun loadBookings() {
        viewModelScope.launch {
            try {
                // In a real app, you'd get the actual user ID from authentication
                val userId = "demo_user_123"
                val bookingsList = bookingDao.getBookingsByUser(userId)
                _bookings.value = bookingsList.filter { it.status == "Active" }
                
                // Update booked car IDs
                _bookedCarIds.value = bookingsList
                    .filter { it.status == "Active" }
                    .map { it.carId }
                    .toSet()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
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
                val booking = Booking(
                    id = UUID.randomUUID().toString(),
                    userId = "demo_user_123", // In real app, get from auth
                    carId = car.id,
                    carName = car.name,
                    pickupLocation = "Toronto M5V 2T6",
                    dropoffLocation = "Toronto M5V 2T6",
                    pickupDate = startDate,
                    returnDate = endDate,
                    totalCost = totalCost.toInt(),
                    status = "Active"
                )
                
                bookingDao.insertBooking(booking)
                loadBookings() // Refresh the list
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    fun cancelBooking(booking: Booking, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                bookingDao.deleteBooking(booking)
                loadBookings() // Refresh the list
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    fun getBookingById(bookingId: String): Booking? {
        return _bookings.value.firstOrNull { it.id == bookingId }
    }
}
