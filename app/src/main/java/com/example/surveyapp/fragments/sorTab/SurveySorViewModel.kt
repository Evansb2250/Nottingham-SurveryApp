package com.example.surveyapp.fragments.sorTab

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.example.surveyapp.CONSTANTS.ExistingSors
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.domains.SoR
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch

class SurveySorViewModel(private val repository: DatabaseRepository) : ViewModel() {



    fun returnListSORLIST(): List<SurveySORs> {
        return addedSor2List
    }

    /*****
    Variable for creating a new SoR code attached to the survey
     ****/

    //Store the total for the Sor USED for both the SorViewModel and data in the SurveySORs data class
    val total = MutableLiveData<Double>()
    var recentlyRemovedSorCode:String =""

    //Number selected in the number wheel
    //TODO change to a text entry to add floating point numbers
    val sorCodeQuantity = MutableLiveData<Int>()
    lateinit var UOM: String
    lateinit var sorDescrip: String
    private var category = ""

    val comments: String

    /*****
    Variables representing the data appeared on the Fragment and ViewModel
     ****/
    //Individual Sor
    var currentSor: SoR? = null

    //
    var searchViewEntry: String = ""


    var addedSors = mutableListOf<String>()
    var addedSor2List = mutableListOf<SurveySORs>()


    // the list that is shown to the Fragment
    var viewList: LiveData<List<String>>


    // a list that is used to intantiate in the ViewList constructor for the MutableLiveData
    // Used to show SORCodes that have a specific word in the description box
    var listForView = mutableListOf<String>()
    var listForViewSize= MutableLiveData<Int>()


    //indicates if the search was successful
    // TODO add more functions to interact and indicate if it is working
    var searchWasFound: Boolean



    //Stores and tracks the recharge amount
    // Protected by Encapsulation
    val rechargeAmount: LiveData<Double> get() = _rechargeAmount
    val _rechargeAmount = MutableLiveData<Double>()


    //Indicator if an insert option fails
    val wasSorInsertedToSurvey: LiveData<Boolean> get() = _wasSorInsertedToSurvey
    private val _wasSorInsertedToSurvey = MutableLiveData<Boolean>()





    // String Description of the SOR
    val sorDescripition: LiveData<String> get() = _sorDescripition
    private val _sorDescripition = MutableLiveData<String>()




    //Value of spinner object for searching
    var searchby = MutableLiveData<String>()




    //Value
    var sorcodeToDeleteIndex: Int? = null


    /***

    INITITIATING Variables

     ****/

    init {
        //TODO implement this portion after description
//        _searchResult.value = null

        listForViewSize.value = 0

        searchby.value = constant.SORCODE
        searchWasFound = true
        _sorDescripition.value = ""
        _rechargeAmount.value = 0.0
        sorCodeQuantity.value = 0
        total.value = 0.0
        viewList = MutableLiveData(listForView)
        comments = " "


    }

    /*************************
     *
     *
     *
    Function calls
     *
     *
     * *************************/


    fun searchFor(userInput: String) {
        when (searchby.value.toString()) {
            constant.SORCODE -> {
                setCurrentToNull()
                searchBySorCode(userInput.toUpperCase())
            }
            constant.KEYWORD -> {
                setCurrentToNull()
                if(userInput.length > 1)
                searchByKeyword(userInput)
            }
        }
    }

    private fun setCurrentToNull() {
        currentSor = null
        updateCurrentSoR()
    }


    private fun searchByKeyword(userInput: String) {
        getSorList(userInput)
    }


    private fun searchBySorCode(sorCode: String) {
        get(sorCode)
    }


    fun updateTotalByQuantity() {
        total.value = _rechargeAmount.value!!.times(sorCodeQuantity.value!!.toInt())
    }


    fun alertSuccess(result: Boolean) {
        searchWasFound = result
    }


    private fun updateCurrentSoR() {
        _sorDescripition.value = currentSor?.description ?: ""
        _rechargeAmount.value = currentSor?.rechargeRate?: 0.0
        UOM = currentSor?.UOM.toString()?: ""
        sorDescrip = currentSor?.description.toString()?: ""
        total.value = currentSor?.rechargeRate?: 0.0
        alertSuccess(true)
    }




    fun CheckBeforeAddint(
        sorCode: String?,
        surveyId: Int,
        comments: String,
        recharge: Boolean,
        quantity: Int?,
        total: Double?
    ) {
        val passedNullTest = checkForNullVariables(sorCode, surveyId, comments, recharge, quantity, total)
        val passedDuplicateSorTest = runCheck4Duplicates(sorCode?.toLowerCase()?.trim())
        val passedNotallowedTest = passedUniqueSoRTest(sorCode?.toUpperCase()?.trim())



        if (passedNullTest) {

            if (passedDuplicateSorTest && passedNotallowedTest) { // val surveysor = SurveySORs(sorCode!!, surveyId!!, comments!!, recharge!!, quantity!!, total!!)
                addedSor2List.add(
                    SurveySORs(
                        surveyId, sorCode!!, UOM, sorDescrip, comments,
                        recharge, quantity!!.toDouble(), _rechargeAmount.value!!, category      //TODO change this so it reflects the current rate for the sorCode
                    )
                )
                resetCat()
                updateListofAddedSorUI(addedSor2List)
                _wasSorInsertedToSurvey.value = true
            } else
                _wasSorInsertedToSurvey.value = false
        } else
        // Changed variable to false to show insert did not work
            _wasSorInsertedToSurvey.value = false


    }




    fun passedUniqueSoRTest(sorCode: String?): Boolean{
        return !constant.NOTALLOWEDTOENTER.contains(sorCode)
    }

    fun loadPreviousSorList(list: List<SurveySORs>){

        if(list != null) {
            addedSor2List = list.toMutableList()
          //  Log.i("SEARCH","Result in survey sor" + addedSor2List.toString())
            updateListofAddedSorUI(addedSor2List)
            _wasSorInsertedToSurvey.value = true
        }
    }

    private fun resetCat() {
        category = ""
    }


    private fun checkForNullVariables(
        sorCode: String?,
        surveyId: Int,
        comments: String,
        recharge: Boolean,
        quantity: Int?,
        total: Double?
    ): Boolean {
        //Checks to see if there are null variables
        return !(sorCode == null || surveyId == null || comments == null || recharge == null || quantity == null || total == null)
    }


    private fun runCheck4Duplicates(sorCode: String?): Boolean {
        for (sor in addedSor2List) {
            val confirmedSor = sor.sorCode.toLowerCase()
            if (sorCode.equals(confirmedSor)) {
                return false
            }
        }
        return true
    }



    /******
     *
     *  Function calls using the Repository to interact with the Database
     *
     *
     */


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun get(sorCode: String) = viewModelScope.launch {
        currentSor = repository.getSor(sorCode)
        if (currentSor != null) {
            updateCurrentSoR()
        } else
            alertSuccess(false)
    }






    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getSorList(keyword: String) = viewModelScope.launch {
        //    val n = repository.getSoRList(keyword)
        listForView.clear()
        for (sor in ExistingSors.getList()) {
            if (sor.description.toLowerCase().contains(keyword.toLowerCase())) {
                listForView.add("${sor.sorCode}-${sor.description}")
            }
            //This list displays the options available SORS a user can select and find out more about.
            viewList = MutableLiveData(listForView)
        }

    }



    fun removeSorFromList() {

        if(addedSor2List != null){
        if (addedSor2List.size >= 0 && sorcodeToDeleteIndex != null) {
            recentlyRemovedSorCode =  addedSor2List.get(sorcodeToDeleteIndex!!).sorCode
            addedSor2List.removeAt(sorcodeToDeleteIndex!!)
            sorcodeToDeleteIndex = null

        }
        updateListofAddedSorUI(addedSor2List)
        }
    }



   fun removeAllSorFromList(){
       addedSor2List.clear()
        updateListofAddedSorUI(addedSor2List)
    }







    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    private fun updateListofAddedSorUI(addedSorList: MutableList<SurveySORs>) =
        viewModelScope.launch {
            var list = mutableListOf<String>()
            var count = 1
            for (surveySor in addedSorList) { list.add("${count} - ${surveySor.sorCode} ${surveySor.sorDescription}")
               count = count.plus(1)
            }
            addedSors = list

        }

    fun setCategory(cat: String) {
        category = cat

    }




}


/****
 *
 *  ViewModelFactory
 *
 */

class SurveySorViewModelFactory(private val repository: DatabaseRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveySorViewModel::class.java)) {

            return SurveySorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}