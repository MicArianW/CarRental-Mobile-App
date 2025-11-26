package com.example.carrental

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Car::class, User::class, Booking::class, PaymentMethod::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun userDao(): UserDao
    abstract fun bookingDao(): BookingDao
}