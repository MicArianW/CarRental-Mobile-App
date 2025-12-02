package com.example.carrental

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// ------------------------------
// AppDatabase
// Main Room database class for the Car Rental application.
// This class defines the list of tables (entities) and provides
// DAOs for accessing the database.
// ------------------------------

@Database(
    entities = [Car::class, User::class, Booking::class, PaymentMethod::class], // All tables
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class) // Converts custom types (e.g., Date)
abstract class AppDatabase : RoomDatabase() {

    // DAO for car table (CRUD operations on cars)
    abstract fun carDao(): CarDao

    // DAO for user table (login, register, get user info)
    abstract fun userDao(): UserDao

    // DAO for booking table (creating, deleting, loading bookings)
    abstract fun bookingDao(): BookingDao
}
