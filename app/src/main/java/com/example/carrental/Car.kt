package com.example.carrental

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "cars")
@TypeConverters(Converters::class)
data class Car(
    @PrimaryKey val id: String,
    val brand: String,
    val model: String,
    val year: Int,
    val type: String, // Sedan, SUV, etc.
    val transmission: String, // Automatic, Manual
    val fuelType: String, // Gasoline, Diesel, Electric, Hybrid
    val seating: Int,
    val mileage: Int,
    val pricePerDay: Int,
    val isAvailable: Boolean = true,
    val features: List<String> = listOf(),
    val imageUrl: String? = null
)