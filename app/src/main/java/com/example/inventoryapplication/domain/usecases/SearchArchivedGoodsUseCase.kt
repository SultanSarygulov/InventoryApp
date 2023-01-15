package com.example.inventoryapplication.domain.usecases

import androidx.lifecycle.LiveData
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.domain.GoodsRepository

class SearchArchivedGoodsUseCase(private val goodsRepository: GoodsRepository) {

    fun searchArchivedGoods(searchQuery: String): LiveData<MutableList<Goods>> {
        return goodsRepository.searchArchivedGoods(searchQuery)
    }
}