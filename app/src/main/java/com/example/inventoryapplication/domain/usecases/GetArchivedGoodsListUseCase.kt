package com.example.inventoryapplication.domain.usecases

import androidx.lifecycle.LiveData
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.domain.GoodsRepository

class GetArchivedGoodsListUseCase(private val goodsRepository: GoodsRepository) {

    fun getArchivedGoodsList(): LiveData<MutableList<Goods>> {
        return goodsRepository.getArchivedGoodsList()
    }
}