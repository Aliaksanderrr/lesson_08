package com.minchinovich.lesson_08

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.minchinovich.lesson_08.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            //TODO doSomeOperations()
            Toast.makeText(
                this,
                "returned: ${data?.getIntExtra(CalculatorActivity.REQUEST_CODE_CALC, -1) ?: -5}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            //TODO doSome()
        }

        binding.calculatorButton.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            resultLauncher.launch(intent)
        }
    }
}