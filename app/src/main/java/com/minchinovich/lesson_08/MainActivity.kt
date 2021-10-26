package com.minchinovich.lesson_08

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.minchinovich.lesson_08.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val APP_PREFERENCES_FILE = "settings"
        private const val APP_PREFERENCES_LIST_ANSWERS = "list answers"
        private const val KEY_CURRENT_ANSWER = "current answers"
        private const val MAX_LIST_ANSWER_SIZE = 5
    }

    private lateinit var binding: ActivityMainBinding
    private val mPreferences by lazy {
        getSharedPreferences(APP_PREFERENCES_FILE, MODE_PRIVATE)
    }

    private val listAnswer = mutableListOf<String>()
    private var currentAnswer: Int? = null

    private var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            currentAnswer = data?.getIntExtra(CalculatorActivity.REQUEST_CODE_CALC, 0)
            updateUI()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()
        initializeButtonListeners()
        updateUI()
    }

    override fun onStop() {
        super.onStop()
        saveData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_CURRENT_ANSWER, currentAnswer.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val currentAnswerAsString = savedInstanceState.getString(KEY_CURRENT_ANSWER)
        currentAnswer = currentAnswerAsString?.toIntOrNull()
        updateUI()
    }

    private fun updateUI() {
        binding.currentAnswer.text = currentAnswer?.toString() ?: ""
        binding.savedAnswers.text = listAnswer.joinToString("\n")
    }

    private fun initializeButtonListeners() {
        binding.saveButton.setOnClickListener {
            currentAnswer?.let { answer -> listAnswer.add(answer.toString()) }
            while (listAnswer.size > MAX_LIST_ANSWER_SIZE) {
                listAnswer.removeAt(0)
            }
            currentAnswer = null
            updateUI()
        }

        binding.calculatorButton.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun loadData() {
        for (index in 0 until MAX_LIST_ANSWER_SIZE) {
            val answer = mPreferences.getString(APP_PREFERENCES_LIST_ANSWERS.plus(index), "")
            if (answer != null) {
                listAnswer.add(answer)
            }
        }
    }

    private fun saveData() {
        val editor = mPreferences.edit().clear()
        for (answer in listAnswer.withIndex()) {
            editor.putString(APP_PREFERENCES_LIST_ANSWERS.plus(answer.index), answer.value)
        }
        editor.apply()
    }
}
