package com.example.inventoryapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Query

class GoodsRepository(private val goodsDao: GoodsDao) {

    var readAllData: LiveData<List<Goods>> = goodsDao.readAllData()

    var readArchivedData: LiveData<List<Goods>> = goodsDao.readArchivedData()

    fun searchData(searchQuery: String): LiveData<List<Goods>> {
        return goodsDao.searchData(searchQuery)
    }

    suspend fun addGoods(goods: Goods){
        goodsDao.addGoods(goods)
    }

    suspend fun deleteGoods(goods: Goods){
        goodsDao.deleteGoods(goods)
    }

    suspend fun updateGoods(goods: Goods){
        goodsDao.updateUser(goods)
    }
}