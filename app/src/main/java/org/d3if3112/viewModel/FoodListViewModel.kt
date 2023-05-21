package org.d3if3112.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.d3if3112.db.FoodDao
import org.d3if3112.db.FoodEntity
import org.d3if3112.db.TrekDb

class FoodListViewModel(db: FoodDao) : ViewModel() {
    val data = db.getFood()
}