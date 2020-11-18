package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CreateSurveyp2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_surveyp2)


        var button = findViewById<Button>(R.id.button9)
        button.setOnClickListener({ view ->
            changeIntent()
        }
        )
    }

    private fun changeIntent() {
        var intent = Intent(this, SORActivity::class.java)
        startActivity(intent)
    }
}