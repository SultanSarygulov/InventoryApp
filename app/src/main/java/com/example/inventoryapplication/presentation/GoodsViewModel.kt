package com.example.inventoryapplication.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.data.GoodsDatabase
import com.example.inventoryapplication.data.GoodsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoodsViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Goods>>
    val repository: GoodsRepository

    init {
        val goodsDao = GoodsDatabase.getDatabase(application).goodsDao()
        repository = GoodsRepository(goodsDao)
        readAllData = repository.readAllData
    }

    fun addGoods(goods: Goods){
        viewModelScope.launch(Dispatchers.IO ) {
            repository.addGoods(goods)
        }
    }

}