package com.example.surveyapp.fragments.viewSurvey

import android.provider.ContactsContract
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.fragments.sorTab.SurveySorViewModel
import com.example.surveyapp.ignore.Survey
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import java.lang.NumberFormatException
import kotlin.properties.Delegates

class ViewSurveyViewModel (private val repository: DatabaseRepository) : ViewModel() {


    //Boolean
    val changeDetected = MutableLiveData<Boolean>()
    val validResponse = MutableLiveData<Boolean>()
    //Error Message
    var errorMessage:String = ""

    // TempList
    private var tempList :List<Survey>? = null
    //
    val id : LiveData<Int>get() = _id
    private var _id = MutableLiveData<Int>()

    //
    val currentSurvey : LiveData<Survey>get() = _currentSurvey
    private var _currentSurvey = MutableLiveData<Survey>()

    //
    var listOfSurveys = arrayListOf<Survey>()
    var listOfSurveyTitles = arrayListOf<String>()



    /*
    1. enter key detail to search for survey
    2. retrieve survey
           a. search by survey id returns one survey ID, SurveySors, and Checklist
           b. if search by date return by List<>
           c. return list surveyID by address
           d. return list Post Code by
    3. clear list holding search,  add survey to the list
        a. create a function that checks the number of surveys in the list
            if(list has only 1 item)  Display the details in a list
        b.  Hold the survey in a container specifiying as the current Sor, so any action will be directed to the right survey


        WHAT DO I NEED
        1. I need a Mutable Live data, either a boolean or a list
            * A list would be good to hold the surveys, but my main purpose is to continually update the listview
            * In that case a boolean serving as a flag would be simpler.

        2.  I need a function that will search for surveys based on ID number, year, month, day, and address
        3. I need to create a function that sorts the surveys by recent.
        4. I need a function that will merge my searches into a list.
        5.


        Sequence.
        1. use enters a (id, address, year, month, or day)
        2. the data will be sent to the viewModel  where a switch statement will determine which function to call
        3. regardless of the search method entered, a survey or list of surveys will be returned.
        4. (the next part needs to take the returned result and pass it to a function)
        5.  Let the function that adds results to the list take to null paramaters
        (this way we can make a flexible function)
          5A. Clear original entries in the list
          5B. Determine if you are adding a list of surveys, or a single survey to the list.
          5C. add entries to the list
          5D. change the value for the LiveData variable to TRUE


     */



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
            constant.ADDRESS -> {
                val address = surveyDetail.toString()
                 tempList = repository.returnSurveysByAddress(address)
                 checkTempList()

            }
            constant.SEARCH_ID -> {
                try{
                val id = surveyDetail!!.toInt()
                val temp = repository.searchSurveyById(id)
                    if (temp != null) {
                        _currentSurvey.value = temp
                        addToList(null, _currentSurvey.value)
                    }else searchNotFound()
                }catch(e: NumberFormatException){
                    errorMessage ="Only numbers are allowed"
                    validResponse.value = false
                    clearAll()
                }



            }
            constant.MONTH ->{ try{
                val month = surveyDetail!!.toInt()
                tempList = repository.returnSurveysByMonth(month)
                checkTempList()

            }catch (e : NumberFormatException){
                errorMessage = "Enter Month as a number ( 1 = January, 2= February, 3= March )"
                validResponse.value = false
                clearAll()
            }
            }
        constant.YEAR ->{
            try {
                val year = surveyDetail!!.toInt()
                tempList = repository.returnSurveysByYear(year)
                checkTempList()
            }catch(e : NumberFormatException){
                errorMessage = "Year must be entered as ( 2018, 2019, 2020 )"
                validResponse.value = false
                clearAll()
            }

        }
        }

    }


    fun checkTempList(){
        try {
            if (tempList != null && tempList!!.size!! > 0) {
                addToList(tempList, null)
            } else searchNotFound()
        }catch (e : NullPointerException){
            searchNotFound()
        }
    }


    private fun addToList(list:List<Survey>?, survey:Survey?){
        //
        listOfSurveys.clear()
        listOfSurveyTitles.clear()
        if(list != null){
            listOfSurveys = list as ArrayList<Survey>
            updateSurveyTitles()

        }else {
            listOfSurveys.add(survey!!)
            updateSurveyTitles()
        }

        changeDetected.value =true

    }



    private fun updateSurveyTitles() {
        for(x in 0..listOfSurveys.size-1){
            listOfSurveyTitles.add("Survey ID:${listOfSurveys[x].surveyId.toString()}\nDated: ${listOfSurveys[x].day.toString()}/${listOfSurveys[x].month.toString()}/${listOfSurveys[x].year.toString()} ")
        }
    }

    fun changeCurrentSurvey(position: Int) {
    _currentSurvey.value = listOfSurveys[position]
    }

   private fun searchNotFound(){
        errorMessage = "Search Not Found"
        validResponse.value = false
       clearAll()
    }



    private fun clearAll(){
        listOfSurveys.clear()
        listOfSurveyTitles.clear()
        _currentSurvey.value = null
        changeDetected.value =true
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

