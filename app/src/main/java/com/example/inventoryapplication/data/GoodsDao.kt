package com.example.inventoryapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GoodsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGoods(goods: Goods)

    @Query("SELECT * FROM goods_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Goods>>

    @Query("SELECT * FROM goods_table WHERE name LIKE :searchQuery OR brand LIKE :searchQuery ")
    fun searchData(searchQuery: String): LiveData<List<Goods>>
}