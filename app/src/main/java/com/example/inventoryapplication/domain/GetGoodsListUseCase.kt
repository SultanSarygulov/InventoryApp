package com.example.inventoryapplication.domain

import androidx.lifecycle.LiveData
import com.example.inventoryapplication.data.Goods

class GetGoodsListUseCase(private val goodsRepository: GoodsRepository) {

    fun getGoodsList(): LiveData<MutableList<Goods>> {
        return goodsRepository.getGoodsList()
    }
}