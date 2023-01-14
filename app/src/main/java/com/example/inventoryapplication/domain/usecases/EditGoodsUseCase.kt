package com.example.inventoryapplication.domain.usecases

import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.domain.GoodsRepository

class EditGoodsUseCase(private val goodsRepository: GoodsRepository) {

    suspend fun editGoods(goods: Goods){
        goodsRepository.editGoods(goods)
    }
}