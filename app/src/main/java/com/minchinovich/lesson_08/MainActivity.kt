package com.minchinovich.lesson_08

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.minchinovich.lesson_08.databinding.ActivityMainBinding
import java.util.function.Function

private const val MAIN_SCREEN = "main screen"
private const val SECONDARY_SCREEN = "secondary screen"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mainScreenText: String = ""
    private var secondaryScreenText: String = ""

    private var answer: Int = 0
    private var currentFunction: Function<Int, Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            mainScreenText = savedInstanceState.getString(MAIN_SCREEN, "")
            Log.d("TAG", "savedInstanceState not null")
            secondaryScreenText = savedInstanceState.getString(SECONDARY_SCREEN, "")
        }

        binding.mainScreen.text = mainScreenText
        binding.secondaryScreen.text = secondaryScreenText

        initialiseNumberButton()
        initialiseFunctionalButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(MAIN_SCREEN, mainScreenText)
        outState.putString(SECONDARY_SCREEN, secondaryScreenText)
        super.onSaveInstanceState(outState)
    }

    private fun initialiseNumberButton() {
        binding.button1.setOnClickListener {
            mainScreenText = mainScreenText.plus(1)
            binding.mainScreen.text = mainScreenText
        }

        binding.button2.setOnClickListener {
            mainScreenText = mainScreenText.plus(2)
            binding.mainScreen.text = mainScreenText
        }

        binding.button3.setOnClickListener {
            mainScreenText = mainScreenText.plus(3)
            binding.mainScreen.text = mainScreenText
        }

        binding.button4.setOnClickListener {
            mainScreenText = mainScreenText.plus(4)
            binding.mainScreen.text = mainScreenText
        }

        binding.button5.setOnClickListener {
            mainScreenText = mainScreenText.plus(5)
            binding.mainScreen.text = mainScreenText
        }

        binding.button6.setOnClickListener {
            mainScreenText = mainScreenText.plus(6)
            binding.mainScreen.text = mainScreenText
        }

        binding.button7.setOnClickListener {
            mainScreenText = mainScreenText.plus(7)
            binding.mainScreen.text = mainScreenText
        }

        binding.button8.setOnClickListener {
            mainScreenText = mainScreenText.plus(8)
            binding.mainScreen.text = mainScreenText
        }

        binding.button9.setOnClickListener {
            mainScreenText = mainScreenText.plus(9)
            binding.mainScreen.text = mainScreenText
        }

        binding.button0.setOnClickListener {
            mainScreenText = mainScreenText.plus(0)
            binding.mainScreen.text = mainScreenText
        }
    }

    private fun initialiseFunctionalButton() {
        binding.buttonPlus.setOnClickListener {
            //TODO
//            val currentNum = mainScreenText.toInt()
//            answer = currentFunction?.apply(currentNum)!!
//            secondaryScreenText = secondaryScreenText.plus("$mainScreenText+")
//            currentFunction = object : Function<Int, Int{ currentNum: Int -> answer + currentNum }
        }
    }
}