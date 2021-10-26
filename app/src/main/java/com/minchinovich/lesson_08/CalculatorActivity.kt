package com.minchinovich.lesson_08

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.minchinovich.lesson_08.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_CALC = "calculator result"
        private const val MAIN_SCREEN_KEY = "main screen"
        private const val SECONDARY_SCREEN_KEY = "secondary screen"
        private const val TEMP_NUM_KEY = "temp num"
        private const val CURRENT_NUM_KEY = "current num"
        private const val TEMP_ANSWER_KEY = "temp answer"
        private const val CURRENT_FUNCTION_KEY = "current function"
    }

    private lateinit var binding: ActivityCalculatorBinding

    private val errorMessage by lazy { getString(R.string.error_message) }
    private val overflowMessage by lazy { getString(R.string.overflow_message) }
    private val plusSymbol by lazy { getString(R.string.button_plus) }
    private val minusSymbol by lazy { getString(R.string.button_minus) }
    private val divisionSymbol by lazy { getString(R.string.button_division) }
    private val multiplicationSymbol by lazy { getString(R.string.button_multiplication) }

    private var secondaryScreenText = ""
    private var mainScreenText = ""
    private var tempNum: Int? = null
    private var currentNum = 0
    private var tempAnswer = 0
    private var currentFunction = Function.DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainScreen.text = mainScreenText
        binding.secondaryScreen.text = secondaryScreenText

        initialiseNumberButton()
        initialiseFunctionalButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(MAIN_SCREEN_KEY, mainScreenText)
        outState.putString(SECONDARY_SCREEN_KEY, secondaryScreenText)
        tempNum?.let { outState.putInt(TEMP_NUM_KEY, it) }
        outState.putInt(CURRENT_NUM_KEY, currentNum)
        outState.putInt(TEMP_ANSWER_KEY, tempAnswer)
        outState.putString(CURRENT_FUNCTION_KEY, currentFunction.name)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mainScreenText = savedInstanceState.getString(MAIN_SCREEN_KEY, "")
        secondaryScreenText = savedInstanceState.getString(SECONDARY_SCREEN_KEY, "")
        tempNum = savedInstanceState.getInt(TEMP_NUM_KEY)
        currentNum = savedInstanceState.getInt(CURRENT_NUM_KEY)
        tempAnswer = savedInstanceState.getInt(TEMP_ANSWER_KEY)
        currentFunction =
            Function.valueOf(
                savedInstanceState.getString(
                    CURRENT_FUNCTION_KEY,
                    Function.DEFAULT.name
                )
            )
        binding.mainScreen.text = mainScreenText
        binding.secondaryScreen.text = secondaryScreenText
    }

    private fun initialiseNumberButton() {
        binding.button1.setOnClickListener {
            pressNumberButton(1)
        }

        binding.button2.setOnClickListener {
            pressNumberButton(2)
        }

        binding.button3.setOnClickListener {
            pressNumberButton(3)
        }

        binding.button4.setOnClickListener {
            pressNumberButton(4)
        }

        binding.button5.setOnClickListener {
            pressNumberButton(5)
        }

        binding.button6.setOnClickListener {
            pressNumberButton(6)
        }

        binding.button7.setOnClickListener {
            pressNumberButton(7)
        }

        binding.button8.setOnClickListener {
            pressNumberButton(8)
        }

        binding.button9.setOnClickListener {
            pressNumberButton(9)
        }

        binding.button0.setOnClickListener {
            pressNumberButton(0)
        }
    }

    private fun pressNumberButton(selectedNum: Int) {
        try {
            tempNum = tempNum?.let { Function.MULTIPLICATION.func(it, 10) } ?: 0
            tempNum = Function.PLUS.func(tempNum!!, selectedNum)
            currentNum = tempNum ?: 0
            mainScreenText = tempNum?.toString() ?: ""
        } catch (e: IllegalArgumentException) {
            tempNum = null
            currentNum = 0
            mainScreenText = overflowMessage
        }
        binding.mainScreen.text = mainScreenText
    }

    private fun initialiseFunctionalButton() {
        binding.buttonReturn.setOnClickListener {
            val intent = Intent()
                .putExtra(REQUEST_CODE_CALC, tempAnswer)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.buttonC.setOnClickListener {
            currentNum = 0
            tempNum = null
            mainScreenText = ""
            secondaryScreenText = ""
            tempAnswer = 0
            currentFunction = Function.DEFAULT
            binding.mainScreen.text = mainScreenText
            binding.secondaryScreen.text = secondaryScreenText
        }

        binding.buttonEqual.setOnClickListener {
            try {
                tempAnswer = currentFunction.func.invoke(tempAnswer, currentNum)
                mainScreenText = tempAnswer.toString()
            } catch (e: ArithmeticException) {
                tempAnswer = 0
                mainScreenText = errorMessage
            } catch (e: IllegalArgumentException) {
                tempAnswer = 0
                mainScreenText = overflowMessage
            }
            secondaryScreenText = ""
            tempNum = null
            binding.mainScreen.text = mainScreenText
            binding.secondaryScreen.text = secondaryScreenText
        }

        binding.buttonPlus.setOnClickListener {
            pressFunctionalButton(Function.PLUS, plusSymbol)
        }

        binding.buttonMinus.setOnClickListener {
            pressFunctionalButton(Function.MINUS, minusSymbol)
        }

        binding.buttonMultiplication.setOnClickListener {
            pressFunctionalButton(Function.MULTIPLICATION, multiplicationSymbol)
        }

        binding.buttonDivision.setOnClickListener {
            pressFunctionalButton(Function.DIVISION, divisionSymbol)
        }
    }

    private fun pressFunctionalButton(selectedFunction: Function, functionSymbol: String) {
        if (tempNum != null || currentFunction == Function.DEFAULT) {
            secondaryScreenText = secondaryScreenText.plus(" $currentNum $functionSymbol")
            try {
                tempAnswer = currentFunction.func.invoke(tempAnswer, currentNum)
                mainScreenText = ""
            } catch (e: ArithmeticException) {
                tempAnswer = 0
                mainScreenText = errorMessage
                secondaryScreenText = "$tempAnswer $functionSymbol"
            } catch (e: IllegalArgumentException) {
                currentNum = 0
                mainScreenText = overflowMessage
                secondaryScreenText = "$tempAnswer $functionSymbol"
            }
            tempNum = null
            currentNum = 0
            currentFunction = selectedFunction
            binding.mainScreen.text = mainScreenText
            binding.secondaryScreen.text = secondaryScreenText
        } else if (secondaryScreenText.isEmpty()) {
            secondaryScreenText = secondaryScreenText.plus(" $tempAnswer $functionSymbol")
            tempNum = null
            currentNum = 0
            currentFunction = selectedFunction
            mainScreenText = ""
            binding.mainScreen.text = mainScreenText
            binding.secondaryScreen.text = secondaryScreenText
        } else if (tempNum == null && currentFunction != Function.DEFAULT) {
            secondaryScreenText = secondaryScreenText.dropLast(1).plus(functionSymbol)
            currentFunction = selectedFunction
            binding.secondaryScreen.text = secondaryScreenText
        }
    }
}
