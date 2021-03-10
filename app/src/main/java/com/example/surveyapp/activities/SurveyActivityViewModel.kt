package com.example.surveyapp.activities

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.classLoader.RestoreSurveyHelper
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.fragments.createTab.createSurveyViewModel
import com.example.surveyapp.ignore.Survey
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch

class SurveyActivityViewModel (private val repository: DatabaseRepository) : ViewModel() {

    lateinit var restoreSurveyHelperClass:RestoreSurveyHelper


    val id : LiveData<Int>
        get() = _id
    private var _id = MutableLiveData<Int>()


    fun createMessage(messageTpye : String, id: Int) {
        when(messageTpye){
            constant.getExistingSurvey ->  requestExistSurvey(id)
            constant.getLastSurvey -> lastSurvey()
        }
    }


    private fun requestExistSurvey(id: Int) = viewModelScope.launch {
       val existingSurvey = repository.searchSurveyById(id)
       val existingSorCodes = repository.returnSurveySors(id)!!
        restoreSurveyHelperClass = RestoreSurveyHelper(existingSurvey, existingSorCodes)
        _id.value = restoreSurveyHelperClass.getId()
    }


    // create a function that
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun lastSurvey() = viewModelScope.launch {
        val survey = repository.getLastestSurvey()
        if(survey == null){
            _id.value  = 1
        }else
        _id.value = survey.surveyId + 1
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