package com.example.inventoryapplication.domain

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "goods_table")
data class Goods(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val cost: Int,
    val brand: String,
    val amount: Int,
    val photo: Bitmap,
    var archived: Boolean
): Parcelable