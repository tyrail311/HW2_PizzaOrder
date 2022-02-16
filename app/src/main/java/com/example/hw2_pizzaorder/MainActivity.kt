package com.example.hw2_pizzaorder

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var total = 0.00
    private val toppingPrice = 1.69
    private val medSizePrice = 9.99
    private val lrgSizePrice = 13.99
    private val xlrgSizePrice = 15.99
    private var tempPrice = 0.00
    val pizzaList = listOf("Pepperoni", "Margheritta", "Hawaiian", "BBQ Chicken")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pizzaAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pizzaList)
        pizza_spinner.adapter = pizzaAdapter
        pizza_spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val imageId = when (position) {
            0 -> R.drawable.pepperoni
            1 -> R.drawable.margheritta
            2 -> R.drawable.hawaiian
            else -> R.drawable.bbq_chicken
        }
        setPizzaChoiceText(pizzaList[position])
        image_pizza.setImageResource(imageId)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(this, "Nothing is selected!", Toast.LENGTH_SHORT).show()
    }

    fun selectToppings(view: View) {
        when (view) {
            extra_cheese -> addToppingCost(extra_cheese)
            mushrooms -> addToppingCost(mushrooms)
            onions -> addToppingCost(onions)
            spinach -> addToppingCost(spinach)
            broccoli -> addToppingCost(broccoli)
            black_olives -> addToppingCost(black_olives)
            else -> addToppingCost(tomatoes)
        }
        displayPrice()
    }

    fun selectSize(view: View)
    {
        when (view) {
            med_pizza_radioButton -> {
                addSizeCost(medSizePrice, tempPrice)
                tempPrice = medSizePrice
            }
            lrg_pizza_radioButton -> {
                addSizeCost(lrgSizePrice, tempPrice)
                tempPrice = lrgSizePrice
            }
            else -> {
                addSizeCost(xlrgSizePrice, tempPrice)
                tempPrice = xlrgSizePrice
            }
        }
        displayPrice()
    }
    private fun addSizeCost(sizePrice : Double, temp: Double)
    {
                total -= temp
                tempPrice = sizePrice
                total += sizePrice
    }
    private fun addToppingCost(view : View)
    {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            if (checked)
                total += toppingPrice
            else
                total -= toppingPrice
        }
    }
    private fun setPizzaChoiceText(selection : String)
    {
        user_pizza_choice_textView.text = selection
    }
    private fun displayPrice()
    {
        val totalPriceNumFormatted:Double = String.format("%.2f", total).toDouble()
        total_price_textView.text = "Total Cost: $$totalPriceNumFormatted"
    }
}