package com.example.surveyapp.fragments.viewSurvey

import android.provider.ContactsContract
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.example.surveyapp.fragments.sorTab.SurveySorViewModel
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class ViewSurveyViewModel (private val repository: DatabaseRepository) : ViewModel() {


    val id : LiveData<Int>get() = _id
    private var _id = MutableLiveData<Int>()

    init{
     //   _id.value = null
    }

    fun createMessage() {
        lastSurvey()
       //Log.i("messageForMe", " Hi I got the message")
    }



    // create a function that
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
     fun lastSurvey() = viewModelScope.launch {
        val survey = repository.getLastestSurvey()
        if(survey == null){
            _id.value = 0
        }else {
            _id.value = survey.surveyId
        }
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

