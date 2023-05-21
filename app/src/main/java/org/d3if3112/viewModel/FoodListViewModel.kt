package org.d3if3112.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3112.db.FoodDao
import org.d3if3112.db.FoodEntity

class FoodListViewModel(private val db: FoodDao) : ViewModel() {
    val data = db.getFood()

    fun insertFood(foodInput: FoodEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.addFood(foodInput)
            }
        }
    }

    fun deleteFood(food: FoodEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.removeFood(food)
            }
        }
    }
}
