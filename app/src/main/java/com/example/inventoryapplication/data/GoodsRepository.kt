package com.example.inventoryapplication.data

import androidx.lifecycle.LiveData

class GoodsRepository(private val goodsDao: GoodsDao) {

    val readAllData: LiveData<List<Goods>> = goodsDao.readAllData()

    suspend fun addGoods(goods: Goods){
        goodsDao.addGoods(goods)
    }
}