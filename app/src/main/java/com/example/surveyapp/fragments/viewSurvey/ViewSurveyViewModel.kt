package com.example.surveyapp.fragments.viewSurvey

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surveyapp.fragments.sorTab.SurveySorViewModel
import com.example.surveyapp.repository.DatabaseRepository

class ViewSurveyViewModel (repository: DatabaseRepository) : ViewModel() {
    fun createMessage() {
       Log.i("messageForMe", " Hi I got the message")
    }


}


class ViewSurveyViewModelFactory(private val repository: DatabaseRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewSurveyViewModel::class.java)) {

            return ViewSurveyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}

