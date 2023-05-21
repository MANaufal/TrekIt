package org.d3if3112.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDao {
    @Insert
    fun addFood(foodEntity: FoodEntity)

    @Query("SELECT * FROM food")
    fun getFood(): LiveData<List<FoodEntity>>

    @Delete
    fun removeFood(foodEntity: FoodEntity)
}