package com.example.inventoryapplication.domain

import com.example.inventoryapplication.data.Goods

class ArchiveGoodsUseCase(private val goodsRepository: GoodsRepository) {

    suspend fun archiveGoods(goods: Goods){
        goodsRepository.archiveGoods(goods)
    }
}