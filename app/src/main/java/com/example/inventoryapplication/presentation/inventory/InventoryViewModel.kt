package com.example.inventoryapplication.presentation.inventory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.data.GoodsDatabase
import com.example.inventoryapplication.data.GoodsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InventoryViewModel(application: Application): AndroidViewModel(application) {

    var readAllData: LiveData<MutableList<Goods>>
    private val repository: GoodsRepositoryImpl

    init {
        val goodsDao = GoodsDatabase.getDatabase(application).goodsDao()
        repository = GoodsRepositoryImpl(goodsDao)
        readAllData = repository.readAllData
    }

    fun addGoods(goods: Goods){
        viewModelScope.launch(Dispatchers.IO ) {
            repository.addGoods(goods)
        }
    }

    fun deleteGoods(goods: Goods){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGoods(goods)
        }
    }

    fun editGoods(goods: Goods){
        viewModelScope.launch(Dispatchers.IO) {
            repository.editGoods(goods)
        }
    }

    fun searchData(searchQuery: String): LiveData<MutableList<Goods>>{
        return repository.searchData(searchQuery)
    }

}