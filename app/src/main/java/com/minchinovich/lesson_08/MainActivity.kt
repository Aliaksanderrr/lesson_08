package com.minchinovich.lesson_08

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.minchinovich.lesson_08.databinding.ActivityMainBinding

private const val MAIN_SCREEN = "main screen"
private const val SECONDARY_SCREEN = "secondary screen"
private const val TEMP_NUM = "temp num"
private const val CURRENT_NUM = "current num"
private const val TEMP_ANSWER = "temp answer"
private const val CURRENT_FUNCTION = "current function"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var secondaryScreenText = ""
    private var mainScreenText = ""
    private var tempNum: Int? = null
    private var currentNum = 0
    private var tempAnswer = 0
    private var currentFunction = FunctionalButton.DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            mainScreenText = savedInstanceState.getString(MAIN_SCREEN, "")
            secondaryScreenText = savedInstanceState.getString(SECONDARY_SCREEN, "")
            tempNum = savedInstanceState.getInt(TEMP_NUM)
            currentNum = savedInstanceState.getInt(CURRENT_NUM, 0)
            tempAnswer = savedInstanceState.getInt(TEMP_ANSWER, 0)
            currentFunction =
                FunctionalButton.valueOf(savedInstanceState.getString(CURRENT_FUNCTION, "DEFAULT"))
        }
        binding.mainScreen.text = mainScreenText
        binding.secondaryScreen.text = secondaryScreenText

        initialiseNumberButton()
        initialiseFunctionalButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(MAIN_SCREEN, mainScreenText)
        outState.putString(SECONDARY_SCREEN, secondaryScreenText)
        tempNum?.let { outState.putInt(TEMP_NUM, it) }
        outState.putInt(CURRENT_NUM, currentNum)
        outState.putInt(TEMP_ANSWER, tempAnswer)
        outState.putString(CURRENT_FUNCTION, currentFunction.name)
        super.onSaveInstanceState(outState)
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
        tempNum = (tempNum?.times(10) ?: 0) + selectedNum
        currentNum = tempNum ?: 0
        mainScreenText = tempNum?.toString() ?: ""
        binding.mainScreen.text = mainScreenText
    }

    private fun initialiseFunctionalButton() {
        binding.buttonEqual.setOnClickListener {
            currentNum = currentFunction.func?.invoke(tempAnswer, currentNum) ?: currentNum
            tempAnswer = 0
            secondaryScreenText = ""
            tempNum = null
            currentFunction = FunctionalButton.DEFAULT
            mainScreenText = ""
            binding.mainScreen.text = currentNum.toString()
            binding.secondaryScreen.text = secondaryScreenText
        }

        binding.buttonC.setOnClickListener {
            currentNum = 0
            tempNum = null
            mainScreenText = ""
            secondaryScreenText = ""
            tempAnswer = 0
            currentFunction = FunctionalButton.DEFAULT
            binding.mainScreen.text = mainScreenText
            binding.secondaryScreen.text = secondaryScreenText
        }

        binding.buttonPlus.setOnClickListener {
            pressFunctionalButton(FunctionalButton.PLUS, "+")
        }

        binding.buttonMinus.setOnClickListener {
            pressFunctionalButton(FunctionalButton.MINUS, "-")
        }

        binding.buttonMultiplication.setOnClickListener {
            pressFunctionalButton(FunctionalButton.MULTIPLICATION, "*")
        }

        binding.buttonDivision.setOnClickListener {
            pressFunctionalButton(FunctionalButton.DIVISION, "/")
        }
    }

    private fun pressFunctionalButton(selectedFunction: FunctionalButton, functionSymbol: String) {
        if (tempNum != null || currentFunction == FunctionalButton.DEFAULT) {
            secondaryScreenText = secondaryScreenText.plus(" $currentNum $functionSymbol")
            tempAnswer = currentFunction.func?.invoke(tempAnswer, currentNum) ?: currentNum
            tempNum = null
            mainScreenText = ""
            currentNum = 0
            currentFunction = selectedFunction
            binding.mainScreen.text = mainScreenText
            binding.secondaryScreen.text = secondaryScreenText
        } else if (tempNum == null && currentFunction != FunctionalButton.DEFAULT) {
            secondaryScreenText = secondaryScreenText.dropLast(1).plus(functionSymbol)
            currentFunction = selectedFunction
            binding.mainScreen.text = mainScreenText
            binding.secondaryScreen.text = secondaryScreenText
        }
    }
}