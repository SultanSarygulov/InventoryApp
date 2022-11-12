package com.example.inventoryapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Query

class GoodsRepository(private val goodsDao: GoodsDao) {

    val readAllData: LiveData<List<Goods>> = goodsDao.readAllData()

    fun searchData(searchQuery: String): LiveData<List<Goods>> {
        return goodsDao.searchData(searchQuery)
    }

    suspend fun addGoods(goods: Goods){
        goodsDao.addGoods(goods)
    }
}