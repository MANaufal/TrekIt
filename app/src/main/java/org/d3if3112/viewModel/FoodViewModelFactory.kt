package org.d3if3112.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3112.db.FoodDao

class FoodViewModelFactory(private val db: FoodDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(FoodListViewModel::class.java)) {
            return FoodListViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}