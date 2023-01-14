package com.example.inventoryapplication.presentation.archive

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.data.GoodsDatabase
import com.example.inventoryapplication.data.GoodsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArchiveViewModel(application: Application): AndroidViewModel(application) {

    var readArchivedData: LiveData<MutableList<Goods>>
    private val repository: GoodsRepositoryImpl

    init {
        val goodsDao = GoodsDatabase.getDatabase(application).goodsDao()
        repository = GoodsRepositoryImpl(goodsDao)
        readArchivedData = repository.readArchivedData
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

    fun searchArchivedGoods(searchQuery: String): LiveData<MutableList<Goods>>{
        return repository.searchArchivedData(searchQuery)
    }

}