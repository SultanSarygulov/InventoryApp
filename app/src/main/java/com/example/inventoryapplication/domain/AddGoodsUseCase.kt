package com.example.inventoryapplication.domain

import com.example.inventoryapplication.data.Goods

class AddGoodsUseCase(private val goodsRepository: GoodsRepository) {

    suspend fun addGoods(goods: Goods){
        goodsRepository.addGoods(goods)
    }
}