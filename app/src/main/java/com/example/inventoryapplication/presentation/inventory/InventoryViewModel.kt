package com.example.inventoryapplication.presentation.inventory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.data.GoodsDatabase
import com.example.inventoryapplication.data.GoodsRepoImpl
import com.example.inventoryapplication.data.GoodsRepositoryImpl
import com.example.inventoryapplication.domain.usecases.AddGoodsUseCase
import com.example.inventoryapplication.domain.usecases.DeleteGoodsUseCase
import com.example.inventoryapplication.domain.usecases.EditGoodsUseCase
import com.example.inventoryapplication.domain.usecases.GetGoodsListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InventoryViewModel(application: Application): AndroidViewModel(application) {


    private val repository: GoodsRepositoryImpl
    private val repo: GoodsRepoImpl

//    var readAllData: LiveData<MutableList<Goods>>

    var goodsList = MutableLiveData<MutableList<Goods>>()

    init {
        val goodsDao = GoodsDatabase.getDatabase(application).goodsDao()
        repository = GoodsRepositoryImpl(goodsDao)
//        readAllData = repository.readAllData

        repo = GoodsRepoImpl(goodsDao)
    }

    private val addGoodsUseCase = AddGoodsUseCase(repo)
    private val deleteGoodsUseCase = DeleteGoodsUseCase(repo)
    private val editGoodsUseCase = EditGoodsUseCase(repo)
    private val getGoodsListUseCase = GetGoodsListUseCase(repo)

    fun addGoods(goods: Goods){
        viewModelScope.launch(Dispatchers.IO ) {
            addGoodsUseCase.addGoods(goods)
        }
    }

    fun deleteGoods(goods: Goods){
        viewModelScope.launch(Dispatchers.IO) {
            deleteGoodsUseCase.deleteGoods(goods)
        }
    }

    fun editGoods(goods: Goods){
        viewModelScope.launch(Dispatchers.IO) {
            editGoodsUseCase.editGoods(goods)
        }
    }

    fun getGoodsList(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = getGoodsListUseCase.getGoodsList()
            goodsList.postValue(list)
        }
    }

    fun searchData(searchQuery: String): LiveData<MutableList<Goods>>{
        return repository.searchData(searchQuery)
    }

}