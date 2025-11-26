package com.example.carrental

import android.content.Context
import androidx.room.Room

class CarRepository(context: Context) {
    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "car_rental_db"
    ).build()

    private val carDao = db.carDao()

    suspend fun getAllCars() = carDao.getAllCars()
    suspend fun getCarById(id: String) = carDao.getCarById(id)
    suspend fun insertCar(car: Car) = carDao.insertCar(car)
    suspend fun deleteCar(car: Car) = carDao.deleteCar(car)
}