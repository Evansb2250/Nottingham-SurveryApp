package com.example.surveyapp.activities

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.surveyapp.R
import com.example.surveyapp.application.SurveyApplication
import com.example.surveyapp.fragments.sorTab.SurveySorViewModel
import com.example.surveyapp.fragments.sorTab.SurveySorViewModelFactory

class MainActivity : AppCompatActivity() {
    companion object{

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: MainActivityViewModel by viewModels { MainActivityViewModelFactory((application as SurveyApplication).repository) }
    }

//    override fun onBackPressed() {
//        Toast.makeText(this, "Go To cancel button to exit Survey", Toast.LENGTH_LONG).show()
//    }
}