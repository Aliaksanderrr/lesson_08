package com.minchinovich.lesson_08

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.minchinovich.lesson_08.databinding.ActivityMainBinding

private const val MAIN_SCREEN = "main screen"
private const val SECONDARY_SCREEN = "secondary screen"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mainScreenText: Int = 0
    private var secondaryScreenText: String = ""

    private var answer: Int = 0
    private var currentNum = 0
    private var currentFunction: ((num1: Int, num2: Int) -> Int)? = { _, num1 -> num1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            mainScreenText = savedInstanceState.getInt(MAIN_SCREEN, 0)
            Log.d("TAG", "savedInstanceState not null")
            secondaryScreenText = savedInstanceState.getString(SECONDARY_SCREEN, "")
        }

        binding.mainScreen.text = mainScreenText.toString()
        binding.secondaryScreen.text = secondaryScreenText

        initialiseNumberButton()
        initialiseFunctionalButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mainScreenText.let { outState.putInt(MAIN_SCREEN, it) }
        outState.putString(SECONDARY_SCREEN, secondaryScreenText)
        super.onSaveInstanceState(outState)
    }

    private fun initialiseNumberButton() {
        binding.button1.setOnClickListener {
            mainScreenText = mainScreenText * 10 + 1
            updateUI()
        }

        binding.button2.setOnClickListener {
            mainScreenText = mainScreenText * 10 + 2
            updateUI()
        }

        binding.button3.setOnClickListener {
            mainScreenText = mainScreenText * 10 + 3
            updateUI()
        }

        binding.button4.setOnClickListener {
            mainScreenText = mainScreenText * 10 + 4
            updateUI()
        }

        binding.button5.setOnClickListener {
            mainScreenText = mainScreenText * 10 + 5
            updateUI()
        }

        binding.button6.setOnClickListener {
            mainScreenText = mainScreenText * 10 + 6
            updateUI()
        }

        binding.button7.setOnClickListener {
            mainScreenText = mainScreenText * 10 + 7
            updateUI()
        }

        binding.button8.setOnClickListener {
            mainScreenText = mainScreenText * 10 + 8
            updateUI()
        }

        binding.button9.setOnClickListener {
            mainScreenText = mainScreenText * 10 + 9
            updateUI()
        }

        binding.button0.setOnClickListener {
            mainScreenText *= 10
            updateUI()
        }
    }

    private fun initialiseFunctionalButton() {
        binding.buttonEqual.setOnClickListener {
            answer = currentFunction?.invoke(answer, mainScreenText) ?: binding.mainScreen.text.toString().toInt()
            mainScreenText = answer
            answer = 0
            secondaryScreenText = ""
            currentFunction = null
            updateUI()
            mainScreenText = 0
//            mainScreenText = currentFunction?.invoke(answer, mainScreenText) ?: answer
//            answer = 0
//            secondaryScreenText = ""
//            currentFunction = null
//            updateUI()
//            mainScreenText = 0
        }

        binding.buttonPlus.setOnClickListener {
            //TODO
            if (currentFunction == null){
                answer = binding.mainScreen.text.toString().toInt()
                secondaryScreenText = secondaryScreenText.plus(" $answer + ")
            } else {
                secondaryScreenText = secondaryScreenText.plus(" $mainScreenText + ")
                answer = currentFunction?.invoke(answer, mainScreenText) ?: answer
            }
            mainScreenText = 0
            currentFunction = { num1, num2 -> num1 + num2 }
            updateUI()
        }

        binding.buttonMinus.setOnClickListener {
            //TODO
            secondaryScreenText = secondaryScreenText.plus(" $mainScreenText - ")
            val answer1 = currentFunction?.invoke(answer, mainScreenText) ?: answer
            mainScreenText = 0
            answer = answer1
            currentFunction = { num1, num2 -> num1 - num2 }
            updateUI()
        }

        binding.buttonC.setOnClickListener {
            mainScreenText = 0
            secondaryScreenText = ""
            answer = 0
            currentFunction = { _, num1 -> num1 }
            updateUI()
        }
    }

    private fun updateUI() {
        binding.mainScreen.text = mainScreenText.toString()
        binding.secondaryScreen.text = secondaryScreenText
    }
}