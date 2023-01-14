package com.example.inventoryapplication.domain

import com.example.inventoryapplication.data.Goods

class GetGoodsUseCase(private val goodsRepository: GoodsRepository) {

    fun getGoods(): Goods {
        return goodsRepository.getGoods()
    }
}