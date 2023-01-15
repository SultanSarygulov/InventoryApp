package com.example.inventoryapplication.presentation.inventory

import android.app.Application
import androidx.lifecycle.*
import com.example.inventoryapplication.data.GoodsRepoImpl
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.domain.GoodsRepository
import com.example.inventoryapplication.domain.usecases.AddGoodsUseCase
import com.example.inventoryapplication.domain.usecases.DeleteGoodsUseCase
import com.example.inventoryapplication.domain.usecases.EditGoodsUseCase
import com.example.inventoryapplication.domain.usecases.GetGoodsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel
@Inject
constructor(private val repository: GoodsRepository): ViewModel() {

    val goodsList = repository.getGoodsList()

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

    fun archiveGoods(goods: Goods){
        viewModelScope.launch(Dispatchers.IO) {
            repository.archiveGoods(goods)
        }
    }

    fun searchGoods(searchQuery: String): LiveData<MutableList<Goods>>{
        return repository.searchGoods(searchQuery)
    }

}