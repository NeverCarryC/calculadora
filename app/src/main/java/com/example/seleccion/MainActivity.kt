package com.example.seleccion

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.seleccion.databinding.ActivityMainBinding
import kotlin.math.*
import android.view.View.OnClickListener
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var currentInput = ""

    private var value1: Double? = null // Primer valor
    private var value2: Double? = null // Segundo valor
    private var operator: String? = null // operador

    private var shortReset = false // cuando el usuario hace clic a un buton de operador, necesita actualizar el currenteInput


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentInput", currentInput)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentInput = savedInstanceState.getString("currentInput", "0") ?: "0"
        binding.display.text = currentInput
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.display.text = currentInput
        setButtonListeners()
    }

    private fun setButtonListeners() {
        //  binding.btnDecremento.setOnClickListener(this)
        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)
        binding.btnAdd.setOnClickListener(this)
        binding.btnSubtract.setOnClickListener(this)
        binding.btnMultiply.setOnClickListener(this)
        binding.btnDivide.setOnClickListener(this)
        binding.btnClear.setOnClickListener(this)
        binding.btnEqual.setOnClickListener(this)
        binding.btnpoint?.setOnClickListener(this)

        binding.btnSin?.setOnClickListener(this)
        binding.btnCos?.setOnClickListener(this)
        binding.btnTan?.setOnClickListener(this)
        binding.btnLog?.setOnClickListener(this)
        binding.btnPow?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.btn0.id -> appendToInput("0")
            binding.btn1.id -> appendToInput("1")
            binding.btn2.id -> appendToInput("2")
            binding.btn3.id -> appendToInput("3")
            binding.btn4.id -> appendToInput("4")
            binding.btn5.id -> appendToInput("5")
            binding.btn6.id -> appendToInput("6")
            binding.btn7.id -> appendToInput("7")
            binding.btn8.id -> appendToInput("8")
            binding.btn9.id -> appendToInput("9")
            binding.btnpoint?.id -> appendToInput(".")

            binding.btnAdd.id -> clickOperator("+")
            binding.btnSubtract.id -> clickOperator("-")
            binding.btnMultiply.id -> clickOperator("*")
            binding.btnDivide.id -> clickOperator("/")
            binding.btnClear.id -> clearInput()
            binding.btnEqual.id -> calculateEqual()


            binding.btnSin?.id -> calculateScientific("sin")
            binding.btnCos?.id -> calculateScientific("cos")
            binding.btnTan?.id -> calculateScientific("tan")
            binding.btnLog?.id -> calculateScientific("log")
            binding.btnPow?.id -> clickOperator("^")
        }
    }





    private fun appendToInput(value: String) {

        val countPoint = currentInput.count { it == '.' }
        val pointPosition = currentInput.indexOf('.')
//        Log.v("point number", countPoint.toString())
//        Log.v("pointPosition", pointPosition.toString())

        if(countPoint == 0){

            if (shortReset == true){
                currentInput = value;
                binding.display.text = currentInput
                shortReset = false
            }else{
                currentInput += value
                binding.display.text = currentInput
            }
        }
        else if(countPoint  > 1 || pointPosition == 0){
            Toast.makeText(
                /* context */ this,
                "Error en el punto decimal.",
                Toast.LENGTH_SHORT // 或 Toast.LENGTH_LONG
            ).show()
        } else if(countPoint == 1){
            if(value == "."){
                Log.v("clickmessage", "usuario introduce . otra vez")
            }else{
                if (shortReset == true){
                    currentInput = value;
                    binding.display.text = currentInput
                    shortReset = false
                }else{
                    currentInput += value
                    binding.display.text = currentInput
                }
            }
        }
        Log.v("currentInput", currentInput)
    }

    private fun clearInput() {
        operator = null
        value1 = null
        value2 = null
        currentInput = ""
        binding.display.text = "0"
    }


    private fun calculateEqual(){
        value2 = currentInput.toDouble();
        Log.v("Valor 1： ",value1.toString())
        Log.v("Valor 2： ",value2.toString())
        operator?.let { Log.v("Operator： ", it) }
        var result = 0.0

        if(operator == "+" && value1 != null && value2 != null){
            result  =(value1 ?: 0.0) + (value2 ?: 0.0)


        } else if(operator == "-" && value1 != null && value2 != null){
            result  =(value1 ?: 0.0) - (value2 ?: 0.0)


        }else if(operator == "*" && value1 != null && value2 != null){
            result  =(value1 ?: 0.0) * (value2 ?: 0.0)


        }else if(operator == "/" && value1 != null && value2 != null){
            if (value2 == 0.0) {
                Toast.makeText(this, "El divisor no puede ser cero.", Toast.LENGTH_SHORT).show()
            } else {
                result = (value1 ?: 0.0) / (value2 ?: 0.0)

            }
        }else if(operator == "^" && value1 != null && value2 != null){
            result  = Math.pow(value1 ?: 0.0, value2 ?: 0.0)

        }

        binding.display.text = result.toString()
        currentInput = result.toString()
        value1 = result
        value2 = null
        shortReset = true
    }

    private fun clickOperator(operator: String) {

        this.operator = operator

        Log.v("Operator： ", operator)

            value1 = currentInput.toDouble()

//        else if(value1 != null && value2 == null){
//            value2 = currentInput.toDouble()
//            Log.v("when click operator, Valor 2： ",value1.toString())
//        }
        shortReset = true
        Log.v("Valor 1： ",value1.toString())

    }




    private fun calculateScientific(operation: String) {
        try {
            val value = currentInput.toDouble()
            val radians = Math.toRadians(value)
            val result = when (operation) {
                "sin" -> sin(radians)
                "cos" -> cos(radians)
                "tan" -> tan(radians)
                "log" -> log10(value)
                else -> throw IllegalArgumentException("Invalid operation")
            }
            Log.v("Sin ", result.toString())
            val roundedResult = "%.2f".format(result)
            binding.display.text = roundedResult.toString()
            currentInput = roundedResult.toString()
        } catch (e: Exception) {
            binding.display.text = "Error"
        }
    }
}
