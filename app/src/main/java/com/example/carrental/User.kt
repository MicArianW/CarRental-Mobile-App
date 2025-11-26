package com.example.carrental

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val password: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)