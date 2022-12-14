package com.example.inventoryapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Query

class GoodsRepository(private val goodsDao: GoodsDao) {

    var readAllData: LiveData<MutableList<Goods>> = goodsDao.readAllData()

    var readArchivedData: LiveData<MutableList<Goods>> = goodsDao.readArchivedData()

    fun searchData(searchQuery: String): LiveData<MutableList<Goods>> {
        return goodsDao.searchData(searchQuery)
    }

    fun searchArchivedData(searchQuery: String): LiveData<MutableList<Goods>> {
        return goodsDao.searchArchivedData(searchQuery)
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