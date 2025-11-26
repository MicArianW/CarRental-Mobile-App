package com.example.carrental

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

// Vehicle categories for filtering
enum class VehicleType { CAR, SUV, TRUCK, VAN }

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey val id: String,
    val name: String,
    val pricePerDay: String,
    @DrawableRes val imageRes: Int,
    val km: String,
    val fuelType: String,
    val seats: Int,
    val transmission: String,
    val vehicleType: VehicleType   // <-- REQUIRED for filters
)

fun getSampleCars(): List<Car> = listOf(
    Car(
        id = "elantra2020",
        name = "Hyundai Elantra 2020",
        pricePerDay = "$40/day",
        imageRes = R.drawable.hyundai_elantra_2020,
        km = "50,000 km",
        fuelType = "Gasoline",
        seats = 5,
        transmission = "Automatic",
        vehicleType = VehicleType.CAR
    ),
    Car(
        id = "corolla2021",
        name = "Toyota Corolla 2021",
        pricePerDay = "$45/day",
        imageRes = R.drawable.toyota_corolla_2021,
        km = "35,000 km",
        fuelType = "Gasoline",
        seats = 5,
        transmission = "Automatic",
        vehicleType = VehicleType.CAR
    ),
    Car(
        id = "civic2022",
        name = "Honda Civic 2022",
        pricePerDay = "$55/day",
        imageRes = R.drawable.honda_civic_2022,
        km = "25,000 km",
        fuelType = "Gasoline",
        seats = 5,
        transmission = "Automatic",
        vehicleType = VehicleType.CAR
    ),
    Car(
        id = "ram2023",
        name = "Dodge Ram 1500 2023",
        pricePerDay = "$80/day",
        imageRes = R.drawable.ram_1500_2023,
        km = "15,000 km",
        fuelType = "Gasoline",
        seats = 5,
        transmission = "Automatic",
        vehicleType = VehicleType.TRUCK    //
    ),
    Car(
        id = "tesla2023",
        name = "Tesla Model 3 2023",
        pricePerDay = "$120/day",
        imageRes = R.drawable.tesla_model_3_2023,
        km = "12,000 km",
        fuelType = "Electric",
        seats = 5,
        transmission = "Automatic",
        vehicleType = VehicleType.CAR
    )
)

// Optional helper
fun getSampleCarById(id: String): Car? =
    getSampleCars().firstOrNull { it.id == id }

