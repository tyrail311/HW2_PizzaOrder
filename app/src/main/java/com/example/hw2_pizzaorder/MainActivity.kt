package com.example.hw2_pizzaorder

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var total = 0.00
    private var totalWithTax = 0.00
    private var totalToppingPrice = 0.00
    private var totalTaxPrice = 0.00
    private var tempPrice = 0.00
    private val toppingPrice = 1.69
    private val medSizePrice = 9.99
    private val lrgSizePrice = 13.99
    private val xlrgSizePrice = 15.99
    private val deliveryPrice = 2.00
    private val pizzaList = listOf("Pepperoni", "Margheritta", "Hawaiian", "BBQ Chicken")


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

    fun selectSize(view: View) {
        when (view) {
            med_pizza_radioButton -> addSizeCost(medSizePrice, tempPrice)
            lrg_pizza_radioButton -> addSizeCost(lrgSizePrice, tempPrice)
            else -> addSizeCost(xlrgSizePrice, tempPrice)
        }
        displayPrice()
    }

    fun delivery(view: View) {
        val deliveryPriceNumFormatted: Double = String.format("%.2f", deliveryPrice).toDouble()
        if (delivery_switch.isChecked) {
            delivery_switch.text = "Yes, $$deliveryPriceNumFormatted"
            total += deliveryPrice
        } else {
            delivery_switch.text = "No, $0.00"
            total -= deliveryPrice
        }
        displayPrice()
    }

    private fun addSizeCost(sizePrice: Double, temp: Double) {
        total -= temp
        tempPrice = sizePrice
        total += sizePrice
    }

    private fun addToppingCost(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            if (checked) {
                total += toppingPrice
                totalToppingPrice += toppingPrice
            } else {
                total -= toppingPrice
                totalToppingPrice -= toppingPrice
            }
        }
    }

    private fun setPizzaChoiceText(selection: String) {
        user_pizza_choice_textView.text = selection
    }

    private fun displayPrice() {
        totalTaxPrice = total * .0635
        totalWithTax = total + totalTaxPrice
        val totalPriceNumFormatted: Double = String.format("%.2f", total).toDouble()
        val totalTaxPriceNumFormatted: Double = String.format("%.2f", totalTaxPrice).toDouble()
        val totalToppingPriceNumFormatted: Double = String.format("%.2f", totalToppingPrice).toDouble()
        val totalWithTaxPriceNumFormatted: Double = String.format("%.2f", totalWithTax).toDouble()
        total_price_textView.text = "Total Price: $$totalPriceNumFormatted"
        tax_price_textView.text = "Taxes: $$totalTaxPriceNumFormatted"
        toppings_price_textView.text = "Toppings: $$totalToppingPriceNumFormatted"
        total_price_with_tax_textView.text = "Total Price (Taxes included): $$totalWithTaxPriceNumFormatted"
    }
}