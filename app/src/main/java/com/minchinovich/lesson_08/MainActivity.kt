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
    private var currentNum = 0
    private var tempAnswer = 0
    private var currentFunction: ((num1: Int, num2: Int) -> Int)? = { _, num1 -> num1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            currentNum = savedInstanceState.getInt(MAIN_SCREEN, 0)
            Log.d("TAG", "savedInstanceState not null")
            secondaryScreenText = savedInstanceState.getString(SECONDARY_SCREEN, "")
        }

        binding.mainScreen.text = currentNum.toString()
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
            currentNum = currentNum * 10 + 1
            updateUI()
        }

        binding.button2.setOnClickListener {
            currentNum = currentNum * 10 + 2
            updateUI()
        }

        binding.button3.setOnClickListener {
            currentNum = currentNum * 10 + 3
            updateUI()
        }

        binding.button4.setOnClickListener {
            currentNum = currentNum * 10 + 4
            updateUI()
        }

        binding.button5.setOnClickListener {
            currentNum = currentNum * 10 + 5
            updateUI()
        }

        binding.button6.setOnClickListener {
            currentNum = currentNum * 10 + 6
            updateUI()
        }

        binding.button7.setOnClickListener {
            currentNum = currentNum * 10 + 7
            updateUI()
        }

        binding.button8.setOnClickListener {
            currentNum = currentNum * 10 + 8
            updateUI()
        }

        binding.button9.setOnClickListener {
            currentNum = currentNum * 10 + 9
            updateUI()
        }

        binding.button0.setOnClickListener {
            currentNum *= 10
            updateUI()
        }
    }

    private fun initialiseFunctionalButton() {
        binding.buttonEqual.setOnClickListener {
            currentNum = currentFunction?.invoke(tempAnswer, currentNum)
                ?: binding.mainScreen.text.toString().toInt()
            tempAnswer = 0
            secondaryScreenText = ""
            currentFunction = null
            updateUI()
            currentNum = 0
        }

        binding.buttonPlus.setOnClickListener {
            if (currentFunction == null) {
                tempAnswer = binding.mainScreen.text.toString().toInt()
                secondaryScreenText = secondaryScreenText.plus(" $tempAnswer + ")
            } else {
                secondaryScreenText = secondaryScreenText.plus(" $currentNum + ")
                tempAnswer = currentFunction?.invoke(tempAnswer, currentNum) ?: tempAnswer
            }
            currentNum = 0
            currentFunction = { num1, num2 -> num1 + num2 }
            updateUI()
        }

        binding.buttonMinus.setOnClickListener {
            if (currentFunction == null) {
                tempAnswer = binding.mainScreen.text.toString().toInt()
                secondaryScreenText = secondaryScreenText.plus(" $tempAnswer - ")
            } else {
                secondaryScreenText = secondaryScreenText.plus(" $currentNum - ")
                tempAnswer = currentFunction?.invoke(tempAnswer, currentNum) ?: tempAnswer
            }
            currentNum = 0
            currentFunction = { num1, num2 -> num1 - num2 }
            updateUI()
        }

        binding.buttonDivision.setOnClickListener {
            if (currentFunction == null) {
                tempAnswer = binding.mainScreen.text.toString().toInt()
                secondaryScreenText = secondaryScreenText.plus(" $tempAnswer / ")
            } else {
                secondaryScreenText = secondaryScreenText.plus(" $currentNum / ")
                tempAnswer = currentFunction?.invoke(tempAnswer, currentNum) ?: tempAnswer
            }
            currentNum = 0
            currentFunction = { num1, num2 -> num1 / num2 }
            updateUI()
        }

        binding.buttonMultiplication.setOnClickListener {
            if (currentFunction == null) {
                tempAnswer = binding.mainScreen.text.toString().toInt()
                secondaryScreenText = secondaryScreenText.plus(" $tempAnswer * ")
            } else {
                secondaryScreenText = secondaryScreenText.plus(" $currentNum * ")
                tempAnswer = currentFunction?.invoke(tempAnswer, currentNum) ?: tempAnswer
            }
            currentNum = 0
            currentFunction = { num1, num2 -> num1 * num2 }
            updateUI()
        }

        binding.buttonC.setOnClickListener {
            currentNum = 0
            secondaryScreenText = ""
            tempAnswer = 0
            currentFunction = { _, num1 -> num1 }
            updateUI()
        }
    }

    private fun updateUI() {
        binding.mainScreen.text = currentNum.toString()
        binding.secondaryScreen.text = secondaryScreenText
    }
}