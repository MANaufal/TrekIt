package org.d3if3112.view

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if3112.R
import org.d3if3112.databinding.FragmentFoodlistBinding
import org.d3if3112.db.FoodEntity
import org.d3if3112.db.TrekDb
import org.d3if3112.model.Food
import org.d3if3112.viewModel.FoodListViewModel
import org.d3if3112.viewModel.FoodViewModelFactory

class foodList: Fragment() {
    private lateinit var binding: FragmentFoodlistBinding
    private lateinit var foodAdapter: FoodAdapter
    private var foodList = ArrayList<Food>()
    private var inputName: String = ""
    private var inputCal: Int = 0

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
                findNavController().navigate(R.id.action_foodList_to_about)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFoodlistBinding.inflate(inflater, container, false)

        binding.addButton.setOnClickListener{
            findNavController().navigate(
                R.id.action_foodList_to_inputFragment
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            foodAdapter = FoodAdapter(object: FoodAdapter.OnItemClickListener {
                override fun onDeleteItem(food: FoodEntity) {
                    showDialog(food)
                }
            })
            with(binding.recyclerViewFragmentFoodList) {
                addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
                adapter = foodAdapter
                setHasFixedSize(true)
            }
            viewModel.data.observe(viewLifecycleOwner, {
                foodAdapter.submitList(it)
            })
    }

    fun showDialog(foodRemove : FoodEntity){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Delete Food?")

        val warningText = TextView(context).apply {
            text = "Warning: Deleting this food item cannot be undone."

            val marginLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                // Set the desired margins
                val marginStart = resources.getDimensionPixelSize(R.dimen.warning_text_margin_start)
                val marginTop = resources.getDimensionPixelSize(R.dimen.warning_text_margin_top)
                val marginEnd = resources.getDimensionPixelSize(R.dimen.warning_text_margin_end)
                val marginBottom = resources.getDimensionPixelSize(R.dimen.warning_text_margin_bottom)
                setMargins(marginStart, marginTop, marginEnd, marginBottom)
            }

            layoutParams = marginLayoutParams
        }

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.addView(warningText)

        builder.setView(layout)

        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            viewModel.deleteFood(foodRemove)
        })

        builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }
}