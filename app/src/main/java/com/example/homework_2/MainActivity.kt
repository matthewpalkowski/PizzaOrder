package com.example.homework_2

/**
 * Class to calculate the total for a pizza order and control the associated UI elements.
 * @author Matthew Palkowski
 */
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Constants
    private val deliveryPrice: Double = 2.00
    private val toppingPrice: Double = 1.69
    private val tax : Double = 0.06

    private val smallPrice: Double = 9.99
    private val mediumPrice: Double = 13.99
    private val largePrice: Double = 15.99

    //Variables
    private var basePizzaPrice = 0.00
    private var extrasTotal: Double = 0.00

    //Components
    private lateinit var deliveryFeeTxt: TextView
    private lateinit var sumTxt: TextView
    private lateinit var taxText: TextView

    private lateinit var image : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeLocalVariables()
        initializeVisibility()
        initializeListeners()
    }

    private fun initializeListeners(){
        var toggleListener: ToggleListener = ToggleListener()
        var rdBtnListener: PizzaRadioButtonListener = PizzaRadioButtonListener()

        findViewById<Spinner>(R.id.spinnerPizza).setOnItemSelectedListener(SpinnerListener())

        findViewById<Switch>(R.id.deliverySwitch).setOnCheckedChangeListener(toggleListener)

        findViewById<CheckBox>(R.id.chkBacon).setOnCheckedChangeListener(toggleListener)
        findViewById<CheckBox>(R.id.chkExtraCheese).setOnCheckedChangeListener(toggleListener)
        findViewById<CheckBox>(R.id.chkMushroom).setOnCheckedChangeListener(toggleListener)
        findViewById<CheckBox>(R.id.chkOlives).setOnCheckedChangeListener(toggleListener)
        findViewById<CheckBox>(R.id.chkOnions).setOnCheckedChangeListener(toggleListener)
        findViewById<CheckBox>(R.id.chkSausage).setOnCheckedChangeListener(toggleListener)
        findViewById<CheckBox>(R.id.chkSpinach).setOnCheckedChangeListener(toggleListener)

        findViewById<RadioButton>(R.id.radioBtnSmall).setOnClickListener(rdBtnListener)
        findViewById<RadioButton>(R.id.radioBtnMedium).setOnClickListener(rdBtnListener)
        findViewById<RadioButton>(R.id.radioBtnLarge).setOnClickListener(rdBtnListener)
    }

    private fun initializeLocalVariables(){
        deliveryFeeTxt = findViewById<TextView>(R.id.txtAddedCharge)
        sumTxt = findViewById<TextView>(R.id.txtSum)
        taxText = findViewById<TextView>(R.id.txtTotalTax)
        image = findViewById<ImageView>(R.id.imageView)
    }

    private fun initializeVisibility(){
        deliveryFeeTxt.visibility = View.INVISIBLE
        sumTxt.visibility = View.INVISIBLE
        taxText.visibility = View.INVISIBLE
        image.setImageResource(R.drawable.pepperoni)
    }

    private fun updateTotal(){
        var sum: Double = basePizzaPrice + extrasTotal
        var tax: Double = sum * tax

        var taxFormatted: String = String.format("%.2f", tax)
        taxText.setText("$" + taxFormatted)
        taxText.visibility = View.VISIBLE

        sum += tax
        val sumNumFormatted : String = String.format("%.2f", sum)
        sumTxt.setText("$" + sumNumFormatted)
        sumTxt.visibility = View.VISIBLE
    }

    private inner class PizzaRadioButtonListener : View.OnClickListener{
        override fun onClick(v: View?) {
            if (v != null) {
                if(v.id == R.id.radioBtnSmall) basePizzaPrice = smallPrice
                if(v.id == R.id.radioBtnMedium) basePizzaPrice = mediumPrice
                if(v.id == R.id.radioBtnLarge) basePizzaPrice = largePrice
            }
            updateTotal()
        }
    }

    private inner class SpinnerListener : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position){
                0 -> image.setImageResource(R.drawable.pepperoni)
                1 -> image.setImageResource(R.drawable.bbq_chicken)
                2 -> image.setImageResource(R.drawable.margheritta)
                else -> {image.setImageResource(R.drawable.hawaiian)}
            }
        }
    }

    private inner class ToggleListener : CompoundButton.OnCheckedChangeListener{
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            if (buttonView is Switch) {
                if (buttonView.isChecked) {
                    deliveryFeeTxt.visibility = View.VISIBLE
                    extrasTotal += deliveryPrice
                } else {
                    extrasTotal -= deliveryPrice
                    deliveryFeeTxt.visibility = View.INVISIBLE
                }
            } else if (buttonView is CheckBox) {
                if (buttonView.isChecked) extrasTotal += toppingPrice
                else extrasTotal -= toppingPrice
            }
            updateTotal()
        }
    }
}