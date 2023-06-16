package org.d3if3112.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if3112.databinding.SearchListBinding
import org.d3if3112.db.FoodEntity
import org.d3if3112.model.Food
import org.d3if3112.viewModel.FoodListViewModel

class SearchAdapter(private val data:List<FoodEntity>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: SearchListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(foodEntity: FoodEntity) = with(binding){
            searchNameView.text = foodEntity.foodName
            searchCalView.text = foodEntity.calories.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}