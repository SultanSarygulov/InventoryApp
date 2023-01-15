package com.example.inventoryapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.inventoryapplication.domain.Goods

@Dao
interface GoodsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGoods(goods: Goods)

    @Delete
    suspend fun deleteGoods(goods: Goods)

    @Update
    suspend fun editGoods(goods: Goods)

    @Query("SELECT * FROM goods_table WHERE archived = 0 ORDER BY id ASC")
    fun readAllData(): MutableList<Goods>

    @Query("SELECT * FROM goods_table WHERE archived = 1 ORDER BY id ASC")
    fun readArchivedData(): LiveData<MutableList<Goods>>

    @Query("SELECT * FROM goods_table WHERE name LIKE :searchQuery AND archived = 0 OR brand LIKE :searchQuery AND archived = 0")
    fun searchData(searchQuery: String): LiveData<MutableList<Goods>>

    @Query("SELECT * FROM goods_table WHERE name LIKE :searchQuery AND archived = 1 OR brand LIKE :searchQuery AND archived = 1")
    fun searchArchivedData(searchQuery: String): LiveData<MutableList<Goods>>
}