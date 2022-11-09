package com.example.inventoryapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goods_table")
data class Goods (
    @PrimaryKey(autoGenerate = true)
    val id: Integer,
    val name: String,
    val cost: Integer,
    val brand: String,
    val amount: Integer
)