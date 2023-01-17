package com.example.inventoryapplication.presentation.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.domain.GoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel
@Inject
constructor(private val repository: GoodsRepository): ViewModel() {

    val archivedGoodsList = repository.getArchivedGoodsList()

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

    fun unarchiveGoods(goods: Goods){
        viewModelScope.launch(Dispatchers.IO) {
            repository.archiveGoods(goods)
        }
    }
}