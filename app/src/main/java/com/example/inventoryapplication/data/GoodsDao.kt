package com.example.inventoryapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GoodsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGoods(goods: Goods)

    @Delete
    suspend fun deleteGoods(goods: Goods)

    @Update
    suspend fun updateUser(goods: Goods)

    @Query("SELECT * FROM goods_table WHERE archived = 0 ORDER BY id ASC")
    fun readAllData(): LiveData<MutableList<Goods>>

    @Query("SELECT * FROM goods_table WHERE archived = 1 ORDER BY id ASC")
    fun readArchivedData(): LiveData<MutableList<Goods>>

    @Query("SELECT * FROM goods_table WHERE name LIKE :searchQuery OR brand LIKE :searchQuery ")
    fun searchData(searchQuery: String): LiveData<MutableList<Goods>>
}