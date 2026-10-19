package com.example.androidlab02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_linear).setOnClickListener {
            val intent = Intent(this, LinearActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_table).setOnClickListener {
            val intent = Intent(this, TableActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_constraint).setOnClickListener {
            val intent = Intent(this, ConstraintCalcActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_space_travel).setOnClickListener {
            val intent = Intent(this, SpaceTravelActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_todo).setOnClickListener {
            val intent = Intent(this, TodoTaskActivity::class.java)
            startActivity(intent)
        }
    }
}
