package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.createSurveyButtonMain)
        button.setOnClickListener({ v ->
            changeFrame()
        })


    }


    fun changeFrame() {
        val intent = Intent(this, CreateSurveyActivity::class.java)
        startActivity(intent)

    }

}