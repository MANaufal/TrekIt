package org.d3if3112.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.d3if3112.databinding.FragmentAddfoodBinding
import org.d3if3112.db.FoodDao
import org.d3if3112.db.FoodEntity
import org.d3if3112.db.TrekDb
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.d3if3112.R
import org.d3if3112.viewModel.FoodListViewModel
import org.d3if3112.viewModel.FoodViewModelFactory

class InputFragment : Fragment() {
    private val viewModel: FoodListViewModel by lazy {
        val db = TrekDb.getInstance(requireContext())
        val factory = FoodViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[FoodListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Enable options menu for the fragment
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> {
                findNavController().navigate(R.id.action_inputFragment_to_about)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentAddfoodBinding = FragmentAddfoodBinding.inflate(inflater, container, false)

        binding.submitButton.setOnClickListener {
            val name = binding.foodNameEditText.text.toString()
            val calories = binding.caloriesEditText.text.toString()

            if(name == ""){
                Toast.makeText(context, R.string.name_invalid, Toast.LENGTH_LONG).show()
            } else if(calories == ""){
                Toast.makeText(context, R.string.cal_invalid, Toast.LENGTH_LONG).show()
            } else {
                val foodIn: FoodEntity = FoodEntity(foodName = name, calories = calories.toDouble())

                viewModel.insertFood(foodIn)

                findNavController().navigateUp()
            }
        }

        return binding.root
    }
}