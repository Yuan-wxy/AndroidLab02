package com.example.androidlab02

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ConstraintCalcActivity : AppCompatActivity() {
    private lateinit var etInput: EditText
    private var expression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_calc)
        etInput = findViewById(R.id.et_input)

        // 数字按钮 0~9 .
        val numButtons = listOf(
            findViewById<Button>(R.id.btn0),
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4),
            findViewById<Button>(R.id.btn5),
            findViewById<Button>(R.id.btn6),
            findViewById<Button>(R.id.btn7),
            findViewById<Button>(R.id.btn8),
            findViewById<Button>(R.id.btn9),
            findViewById<Button>(R.id.btn_dot)
        )

        numButtons.forEach { btn ->
            btn.setOnClickListener {
                expression += btn.text
                etInput.setText(expression)
            }
        }

        // ========== 和你xml匹配！！！ ==========
        // btn_plus1 是 / 除法
        findViewById<Button>(R.id.btn_plus1).setOnClickListener { appendOperator("/") }
        // btn_mul 是 * 乘法
        findViewById<Button>(R.id.btn_mul).setOnClickListener { appendOperator("*") }
        // btn_plus2 是 + 加法
        findViewById<Button>(R.id.btn_plus2).setOnClickListener { appendOperator("+") }
        // btn_minus 是 - 减法
        findViewById<Button>(R.id.btn_minus).setOnClickListener { appendOperator("-") }

        // 等于号，计算
        findViewById<Button>(R.id.btn_eq).setOnClickListener {
            try {
                val result = calculate(expression)
                etInput.setText(result.toString())
                expression = result.toString()
            } catch (e: Exception) {
                etInput.setText("Error")
                expression = ""
            }
        }
    }

    private fun appendOperator(op: String) {
        expression += op
        etInput.setText(expression)
    }

    // 四则运算解析函数
    private fun calculate(expr: String): Double {
        val tokens = expr.split("(?=[-+*/])|(?<=[-+*/])".toRegex())
        var res = tokens[0].toDouble()
        var index = 1
        while (index < tokens.size) {
            val op = tokens[index]
            val num = tokens[index + 1].toDouble()
            when (op) {
                "+" -> res += num
                "-" -> res -= num
                "*" -> res *= num
                "/" -> res /= num
            }
            index += 2
        }
        return res
    }
}
