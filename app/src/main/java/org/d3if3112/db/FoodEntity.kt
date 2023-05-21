package org.d3if3112.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val foodName: String,
    val calories: Int
)
