package org.d3if3112

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.d3if3112.databinding.FoodListBinding

class MainAdapter(private val data: ArrayList<food>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    class ViewHolder(
        private val binding: FoodListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(food: food) = with(binding) {
            nameTextView.text = food.name
            calTextView.text = food.cal.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FoodListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}