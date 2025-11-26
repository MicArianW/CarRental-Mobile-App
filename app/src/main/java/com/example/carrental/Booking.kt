package com.example.carrental

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class Booking(
    @PrimaryKey val id: String,
    val userId: String,
    val carId: String,
    val carName: String,
    val pickupLocation: String,
    val dropoffLocation: String,
    val pickupDate: String,
    val returnDate: String,
    val totalCost: Int,
    val status: String, // Active, Completed, Cancelled
    val createdAt: Long = System.currentTimeMillis()
)