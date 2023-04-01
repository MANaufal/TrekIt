package org.d3if3112

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if3112.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var foodList = ArrayList<food>()
    private lateinit var binding: ActivityMainBinding
    private var inputName: String = ""
    private var inputCal: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = MainAdapter(getData())
            setHasFixedSize(false)
        }

        binding.addButton.setOnClickListener{
            showDialog()
        }
    }

    fun showDialog(){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Add Food")

        val inputString = EditText(this)
        val inputInt = EditText(this)

        var temp: String

        inputString.setHint("Enter food name")
        inputString.inputType = InputType.TYPE_CLASS_TEXT

        inputInt.setHint("Enter Calories")
        inputInt.inputType = InputType.TYPE_CLASS_NUMBER

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.addView(inputString)
        layout.addView(inputInt)

        builder.setView(layout)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            inputName = inputString.text.toString()
            temp = inputInt.text.toString()

            if(inputName == ""){
                Toast.makeText(this, R.string.name_invalid, Toast.LENGTH_LONG).show()
            } else if(temp == ""){
                Toast.makeText(this, R.string.cal_invalid, Toast.LENGTH_LONG).show()
            } else {
                inputCal = inputInt.text.toString().toInt()
                enterData()
            }
        })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun enterData(){
        foodList.add(food(inputName, inputCal))
        binding.recyclerView.adapter?.notifyDataSetChanged()

        inputName = ""
        inputCal = 0
    }

    private fun getData(): ArrayList<food> {
        return foodList;
    }
}