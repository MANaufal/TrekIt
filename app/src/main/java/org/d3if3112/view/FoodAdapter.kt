package org.d3if3112.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if3112.databinding.FoodListBinding
import org.d3if3112.db.FoodEntity
import org.d3if3112.model.Food

class FoodAdapter(private val listener : OnItemClickListener) : ListAdapter<FoodEntity, FoodAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<FoodEntity>() {
                override fun areItemsTheSame(
                    oldData: FoodEntity, newData: FoodEntity
                ): Boolean {
                    return oldData.id == newData.id
                }
                override fun areContentsTheSame(
                    oldData: FoodEntity, newData: FoodEntity
                ): Boolean {
                    return oldData == newData
                }
            }
    }

    interface OnItemClickListener {
        fun onDeleteItem(food: FoodEntity)
    }

    class ViewHolder(
        private val binding: FoodListBinding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: FoodEntity) = with(binding) {
            nameTextView.text = food.foodName
            calTextView.text = food.calories.toString()

            binding.buttonDelete.setOnClickListener{
                listener.onDeleteItem(food)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FoodListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
