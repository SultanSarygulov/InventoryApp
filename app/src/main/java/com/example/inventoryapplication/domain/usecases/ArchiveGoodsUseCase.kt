package com.example.inventoryapplication.domain.usecases

import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.domain.GoodsRepository

class ArchiveGoodsUseCase(private val goodsRepository: GoodsRepository) {

    suspend fun archiveGoods(goods: Goods){
        goodsRepository.archiveGoods(goods)
    }
}