package com.example.inventoryapplication.domain.usecases

import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.domain.GoodsRepository

class DeleteGoodsUseCase(private val goodsRepository: GoodsRepository) {

    suspend fun deleteGoods(goods: Goods){
        goodsRepository.deleteGoods(goods)
    }
}