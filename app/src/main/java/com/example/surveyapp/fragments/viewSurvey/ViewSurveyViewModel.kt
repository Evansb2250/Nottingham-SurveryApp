package com.example.surveyapp.fragments.viewSurvey

import android.provider.ContactsContract
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.fragments.sorTab.SurveySorViewModel
import com.example.surveyapp.ignore.Survey
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class ViewSurveyViewModel (private val repository: DatabaseRepository) : ViewModel() {


    val id : LiveData<Int>get() = _id
    private var _id = MutableLiveData<Int>()


    val currentSurvey : LiveData<Survey>get() = _currentSurvey
    private var _currentSurvey = MutableLiveData<Survey>()


//
//    val id : LiveData<Int>get() = _id
//    private var _id = MutableLiveData<Int>()






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

    fun requestSurvey(searchMethod: Any?, surveyDetail: String?) {
        databaseHandler(searchMethod, surveyDetail)
    }



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    private fun databaseHandler(searchMethod: Any?, surveyDetail: String?) = viewModelScope.launch {

        when(searchMethod){
            "Address" -> {Log.i("SearchMethod", "Address" + surveyDetail)


            }
            "Survey ID" -> {
                val id = surveyDetail!!.toInt()
              _currentSurvey.value =  repository.searchSurveyById(id)
            }
            "Date" ->     Log.i("SearchMethod", "Date" + surveyDetail)
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

