package com.example.inventoryapplication.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.domain.GoodsRepository

class SearchGoodsUseCase(private val goodsRepository: GoodsRepository) {

    fun searchGoods(searchQuery: String): LiveData<MutableList<Goods>> {
        return goodsRepository.searchGoods(searchQuery)
    }
}