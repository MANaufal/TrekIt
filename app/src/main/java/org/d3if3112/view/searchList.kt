package org.d3if3112.view

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3112.R
import org.d3if3112.databinding.FragmentFoodlistBinding
import org.d3if3112.databinding.FragmentSearchBinding
import org.d3if3112.databinding.SearchListBinding
import org.d3if3112.db.FoodEntity
import org.d3if3112.db.TrekDb
import org.d3if3112.network.NutApi
import org.d3if3112.viewModel.FoodListViewModel
import org.d3if3112.viewModel.FoodViewModelFactory

class searchList: Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter

    private val viewModel: FoodListViewModel by lazy {
        val db = TrekDb.getInstance(requireContext())
        val factory = FoodViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[FoodListViewModel::class.java]
    }

    private var data: List<FoodEntity> = listOf()
    private var mutableData: MutableList<FoodEntity> = mutableListOf()

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoadingIndicator()
                fetchApiData(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchAdapter.notifyDataSetChanged()
                return true
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchAdapter = SearchAdapter(mutableData)
        with(binding.recyclerViewFragmentSearchList) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = searchAdapter
            setHasFixedSize(true)
        }
    }

    fun fetchApiData(searchQuery: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    NutApi.service.getFood("08pQHUOsq564g7HLdPrfUg==Fx5ZyPBC7qFXXEqm", searchQuery)
                }

                mutableData.clear()

                if(result != null) {
                    for (food in result) {
                        val name = food.name
                        val calories = food.calories

                        val foodIn: FoodEntity = FoodEntity(foodName = name, calories = calories)
                        mutableData.add(foodIn)
                        Log.d("searchList", name + " " + calories)
                        searchAdapter.notifyDataSetChanged()
                    }
                } else {
                    noResultDialog("Food does not exist. Please try another one")
                }

                hideLoadingIndicator()
            } catch (e: Exception) {
                hideLoadingIndicator()
                showErrorDialog("Loading failed. Please try again.")
                Log.d("searchList", "Failure: ${e.message}")
            }
        }
    }

    private fun showLoadingIndicator() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    private fun hideLoadingIndicator() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    private fun noResultDialog(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("No Search Result")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}