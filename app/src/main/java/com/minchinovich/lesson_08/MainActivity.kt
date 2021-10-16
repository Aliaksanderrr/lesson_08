package com.minchinovich.lesson_08

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.minchinovich.lesson_08.databinding.ActivityMainBinding

private const val MAIN_SCREEN = "main screen"
private const val SECONDARY_SCREEN = "secondary screen"

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
            currentNum = savedInstanceState.getInt(MAIN_SCREEN, 0)
            Log.d("TAG", "savedInstanceState not null")
            secondaryScreenText = savedInstanceState.getString(SECONDARY_SCREEN, "")
        }

        binding.mainScreen.text = ""
        binding.secondaryScreen.text = secondaryScreenText

        initialiseNumberButton()
        initialiseFunctionalButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        currentNum.let { outState.putInt(MAIN_SCREEN, it) }
        outState.putString(SECONDARY_SCREEN, secondaryScreenText)
        super.onSaveInstanceState(outState)
    }

    private fun initialiseNumberButton() {
        binding.button1.setOnClickListener {
            pressNumButton(1)
        }

        binding.button2.setOnClickListener {
            pressNumButton(2)
        }

        binding.button3.setOnClickListener {
            pressNumButton(3)
        }

        binding.button4.setOnClickListener {
            pressNumButton(4)
        }

        binding.button5.setOnClickListener {
            pressNumButton(5)
        }

        binding.button6.setOnClickListener {
            pressNumButton(6)
        }

        binding.button7.setOnClickListener {
            pressNumButton(7)
        }

        binding.button8.setOnClickListener {
            pressNumButton(8)
        }

        binding.button9.setOnClickListener {
            pressNumButton(9)
        }

        binding.button0.setOnClickListener {
            pressNumButton(0)
        }
    }

    private fun pressNumButton(selectedNum: Int) {
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
            pressFunButton(FunctionalButton.PLUS, "+")
        }

        binding.buttonMinus.setOnClickListener {
            pressFunButton(FunctionalButton.MINUS, "-")
        }

        binding.buttonMultiplication.setOnClickListener {
            pressFunButton(FunctionalButton.MULTIPLICATION, "*")
        }

        binding.buttonDivision.setOnClickListener {
            pressFunButton(FunctionalButton.DIVISION, "/")
        }
    }

    private fun pressFunButton(pressFunc: FunctionalButton, funcSymbol: String) {
        if (tempNum != null || currentFunction == FunctionalButton.DEFAULT) {
            secondaryScreenText = secondaryScreenText.plus(" $currentNum $funcSymbol")
            tempAnswer = currentFunction.func?.invoke(tempAnswer, currentNum) ?: currentNum
            tempNum = null
            mainScreenText = ""
            currentNum = 0
            currentFunction = pressFunc
            binding.mainScreen.text = mainScreenText
            binding.secondaryScreen.text = secondaryScreenText
        } else if (tempNum == null && currentFunction != FunctionalButton.DEFAULT) {
            secondaryScreenText = secondaryScreenText.dropLast(1).plus(funcSymbol)
            currentFunction = pressFunc
            binding.mainScreen.text = mainScreenText
            binding.secondaryScreen.text = secondaryScreenText
        }
    }
}