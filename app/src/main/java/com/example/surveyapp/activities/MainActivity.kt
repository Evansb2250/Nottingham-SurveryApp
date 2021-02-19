package com.example.surveyapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.surveyapp.R
import com.example.surveyapp.application.SurveyApplication
import com.example.surveyapp.fragments.sorTab.SurveySorViewModel
import com.example.surveyapp.fragments.sorTab.SurveySorViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: MainActivityViewModel by viewModels { MainActivityViewModelFactory((application as SurveyApplication).repository) }

        viewModel.refreshPage()
    }




}