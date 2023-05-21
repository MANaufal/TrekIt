package org.d3if3112.view

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if3112.R
import org.d3if3112.databinding.FragmentFoodlistBinding
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
            foodAdapter = FoodAdapter()
            with(binding.recyclerViewFragmentFoodList) {
                addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
                adapter = foodAdapter
                setHasFixedSize(true)
            }
            viewModel.data.observe(viewLifecycleOwner, {
                foodAdapter.submitList(it)
            })
    }

    fun showDialog(){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Add Food")

        val inputString = EditText(context)
        val inputInt = EditText(context)

        var temp: String

        inputString.setHint("Enter food name")
        inputString.inputType = InputType.TYPE_CLASS_TEXT

        inputInt.setHint("Enter Calories")
        inputInt.inputType = InputType.TYPE_CLASS_NUMBER

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.addView(inputString)
        layout.addView(inputInt)

        builder.setView(layout)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            inputName = inputString.text.toString()
            temp = inputInt.text.toString()

            if(inputName == ""){
                Toast.makeText(context, R.string.name_invalid, Toast.LENGTH_LONG).show()
            } else if(temp == ""){
                Toast.makeText(context, R.string.cal_invalid, Toast.LENGTH_LONG).show()
            } else {
                inputCal = inputInt.text.toString().toInt()
                enterData()
            }
        })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun enterData(){
        foodList.add(Food(inputName, inputCal, 1))
        binding.recyclerViewFragmentFoodList.adapter?.notifyDataSetChanged()

        inputName = ""
        inputCal = 0
    }

    private fun getData(): ArrayList<Food> {
        return foodList;
    }
}