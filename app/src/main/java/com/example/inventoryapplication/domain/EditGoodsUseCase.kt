package com.example.inventoryapplication.domain

import com.example.inventoryapplication.data.Goods

class EditGoodsUseCase(private val goodsRepository: GoodsRepository) {

    suspend fun editGoods(goods: Goods){
        goodsRepository.editGoods(goods)
    }
}