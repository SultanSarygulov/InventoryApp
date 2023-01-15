package com.example.inventoryapplication.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.domain.GoodsRepository
import javax.inject.Inject

class GoodsRepoImpl
    @Inject
    constructor(private val goodsDao: GoodsDao) : GoodsRepository {

    private val goodsListLiveData = goodsDao.readAllData()

    override suspend fun addGoods(goods: Goods) {
        goodsDao.addGoods(goods)
    }

    override suspend fun archiveGoods(goods: Goods) {

        var archivedStatus = true

        if (goods.archived){
            archivedStatus = false
        }

        val archivedGoods = Goods(
            goods.id,
            goods.name,
            goods.cost,
            goods.brand,
            goods.amount,
            goods.photo,
            archivedStatus)

        editGoods(archivedGoods)
    }

    override suspend fun deleteGoods(goods: Goods) {
        goodsDao.deleteGoods(goods)
    }

    override suspend fun editGoods(goods: Goods) {
        goodsDao.editGoods(goods)
    }

    override fun getGoods(): Goods {
        TODO("Not yet implemented")
    }

    override fun getGoodsList(): LiveData<MutableList<Goods>> {
        Log.d("Chura", "getGoodsList: ${goodsListLiveData.value}")
        return goodsListLiveData
    }

    override fun searchGoods(searchQuery: String): LiveData<MutableList<Goods>> {
        return goodsDao.searchData(searchQuery)
    }
}