package com.example.inventoryapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Goods::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GoodsDatabase: RoomDatabase() {

    abstract fun goodsDao(): GoodsDao

    companion object{
        @Volatile
        private var INSTANCE: GoodsDatabase? = null

        fun getDatabase(context: Context): GoodsDatabase{
            val tempInstance = INSTANCE

            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GoodsDatabase::class.java,
                    "goods_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}