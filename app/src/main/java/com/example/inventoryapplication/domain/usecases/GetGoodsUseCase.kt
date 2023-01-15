package com.example.inventoryapplication.domain.usecases

import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.domain.GoodsRepository

class GetGoodsUseCase(private val goodsRepository: GoodsRepository) {

    fun getGoods(): Goods {
        return goodsRepository.getGoods()
    }
}