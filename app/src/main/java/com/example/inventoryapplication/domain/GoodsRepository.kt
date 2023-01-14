package com.example.inventoryapplication.domain

import androidx.lifecycle.LiveData
import com.example.inventoryapplication.data.Goods

interface GoodsRepository {

    suspend fun addGoods(goods: Goods)

    suspend fun archiveGoods(goods: Goods)

    suspend fun deleteGoods(goods: Goods)

    suspend fun editGoods(goods: Goods)

    fun getGoods(): Goods

    fun getGoodsList(): LiveData<MutableList<Goods>>
}