package com.example.inventoryapplication.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.domain.GoodsRepository

class GetGoodsListUseCase(private val goodsRepository: GoodsRepository) {

    fun getGoodsList(): LiveData<MutableList<Goods>>{
        return goodsRepository.getGoodsList()
    }
}