package com.example.inventoryapplication.domain.usecases

import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.domain.GoodsRepository

class AddGoodsUseCase(private val goodsRepository: GoodsRepository) {

    suspend fun addGoods(goods: Goods){
        goodsRepository.addGoods(goods)
    }
}