package com.example.surveyapp


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity


class CreateSurveyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_survey)


        val button = findViewById<Button>(R.id.nextButton_CS)
        button.setOnClickListener({ v ->
            changeFrame()
        })
    }


    fun changeFrame() {
        val intent = Intent(this, CreateSurveyp2Activity::class.java)
//        var textv= findViewById<EditText>(R.id.editTextTextPersonName7)
//        var name = textv.text.toString()
//        if(name != null){ SurveyData.surveyor = name}

        startActivity(intent)

    }
}


