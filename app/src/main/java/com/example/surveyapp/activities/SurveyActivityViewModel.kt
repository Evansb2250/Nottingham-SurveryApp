package com.example.surveyapp.activities

import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.example.surveyapp.fragments.createTab.createSurveyViewModel
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch

class SurveyActivityViewModel (private val repository: DatabaseRepository) : ViewModel() {




    val id : LiveData<Int>
        get() = _id
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
        _id.value = survey.surveyId
    }

}

class SurveyActivityViewModelFactory(private val repository: DatabaseRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveyActivityViewModel::class.java)) {

            return SurveyActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}