package com.example.inventoryapplication.domain

import androidx.lifecycle.LiveData

interface GoodsRepository {

    suspend fun addGoods(goods: Goods)

    suspend fun archiveGoods(goods: Goods)

    suspend fun deleteGoods(goods: Goods)

    suspend fun editGoods(goods: Goods)

    fun getGoodsList(): LiveData<MutableList<Goods>>

    fun getArchivedGoodsList(): LiveData<MutableList<Goods>>

    fun searchArchivedGoods(searchQuery: String): LiveData<MutableList<Goods>>
}