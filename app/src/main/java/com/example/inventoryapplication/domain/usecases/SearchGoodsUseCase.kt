package com.example.inventoryapplication.domain.usecases

import androidx.lifecycle.LiveData
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.domain.GoodsRepository

//Deprecated
class SearchGoodsUseCase(private val goodsRepository: GoodsRepository) {

//    fun searchGoods(searchQuery: String): LiveData<MutableList<Goods>> {
//        return goodsRepository.searchGoods(searchQuery)
//    }
}