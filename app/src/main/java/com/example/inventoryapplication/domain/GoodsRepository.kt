package com.example.inventoryapplication.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface GoodsRepository {

    suspend fun addGoods(goods: Goods)

    suspend fun archiveGoods(goods: Goods)

    suspend fun deleteGoods(goods: Goods)

    suspend fun editGoods(goods: Goods)

    fun getGoods(): Goods

    suspend fun getGoodsList(): MutableList<Goods>

    fun searchGoods(searchQuery: String): LiveData<MutableList<Goods>>
}