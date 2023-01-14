package com.example.inventoryapplication.domain

import com.example.inventoryapplication.data.Goods

class DeleteGoodsUseCase(private val goodsRepository: GoodsRepository) {

    suspend fun deleteGoods(goods: Goods){
        goodsRepository.deleteGoods(goods)
    }
}