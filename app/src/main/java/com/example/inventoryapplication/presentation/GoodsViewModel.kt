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

    var readAllData: LiveData<List<Goods>>
    var readArchivedData: LiveData<List<Goods>>
    private val repository: GoodsRepository

    init {
        val goodsDao = GoodsDatabase.getDatabase(application).goodsDao()
        repository = GoodsRepository(goodsDao)
        readAllData = repository.readAllData
        readArchivedData = repository.readArchivedData
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

    fun updateGoods(goods: Goods){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGoods(goods)
        }
    }

    fun searchData(searchQuery: String): LiveData<List<Goods>>{
        return repository.searchData(searchQuery)
    }

}