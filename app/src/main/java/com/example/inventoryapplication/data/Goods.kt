package com.example.inventoryapplication.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goods_table")
data class Goods (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val cost: Int,
    val brand: String,
    val amount: Int,
    val photo: Bitmap
)