package com.example.surveyapp.fragments.sorTab

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.example.surveyapp.CONSTANTS.ExistingSors
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.domains.SoR
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class SurveySorViewModel(private val repository: DatabaseRepository) : ViewModel() {


    var addedSorList = mutableListOf<SurveySORs>()


    fun returnListSORLIST(): List<SurveySORs> {
        return addedSorList
    }

    /*****
    Variable for creating a new SoR code attached to the survey
     ****/

    //Store the total for the Sor USED for both the SorViewModel and data in the SurveySORs data class
    val total = MutableLiveData<Double>()

    //Number selected in the number wheel
    //TODO change to a text entry to add floating point numbers
    val quantitySelected = MutableLiveData<Int>()

    val comments: String

    /*****
    Variables representing the data appeared on the Fragment and ViewModel
     ****/
    //Individual Sor
    var currentSor: SoR? = null

    //
    var searchViewEntry: String = ""


    var addedSors = mutableListOf<String>()


    // the list that is shown to the Fragment
    var viewList: LiveData<List<String>>


    // a list that is used to intantiate in the ViewList constructor for the MutableLiveData
    // Used to show SORCodes that have a specific word in the description box
    var listForView = mutableListOf<String>()


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


        searchby.value = constant.SORCODE
        searchWasFound = true
        _sorDescripition.value = ""
        _rechargeAmount.value = 0.0
        quantitySelected.value = 0
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
            constant.SORCODE -> searchBySorCode(userInput.toUpperCase())
            constant.KEYWORD -> searchByKeyword(userInput)
        }
    }


    private fun searchByKeyword(userInput: String) {
        getSorList(userInput)
    }


    private fun searchBySorCode(sorCode: String) {
        get(sorCode)
    }


    fun updateTotalByQuantity() {
        total.value = _rechargeAmount.value!!.times(quantitySelected.value!!.toInt())
    }


    fun alertSuccess(result: Boolean) {
        searchWasFound = result
    }


    private fun updateCurrentSoR() {
        _sorDescripition.value = currentSor?.description
        _rechargeAmount.value = currentSor?.rechargeRate
        total.value = 0.0
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
        val passedNullTest =
            checkForNullVariables(sorCode, surveyId, comments, recharge, quantity, total)
        val passedDuplicateSorTest = runCheck(sorCode?.toLowerCase()?.trim())



        if (passedNullTest) {

            if (passedDuplicateSorTest) { // val surveysor = SurveySORs(sorCode!!, surveyId!!, comments!!, recharge!!, quantity!!, total!!)
                addedSorList.add(
                    SurveySORs(
                        sorCode!!, surveyId, comments,
                        recharge, quantity!!.toDouble(), total!!
                    )

                )

                updateListofAddedSorUI(addedSorList)
                _wasSorInsertedToSurvey.value = true
            } else
                _wasSorInsertedToSurvey.value = false
        } else
        // Changed variable to false to show insert did not work
            _wasSorInsertedToSurvey.value = false


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


    private fun runCheck(sorCode: String?): Boolean {
        for (sor in addedSorList) {
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
    fun insert(sorCode: SoR) = viewModelScope.launch {
        repository.insert(sorCode)

    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getSorList(keyword: String) = viewModelScope.launch {
        //    val n = repository.getSoRList(keyword)
        listForView.clear()
        for (sor in ExistingSors.getList()) {
            if (sor.description.toLowerCase().contains(keyword.toLowerCase())) {
                listForView.add(sor.sorCode)
            }
            viewList = MutableLiveData(listForView)
        }

    }


    fun removeSorFromList() {

//        Log.i("SystemMessage", addedSorList.get(sorcodeToDeleteIndex!!).toString())
        if (addedSorList.size >= 0 && sorcodeToDeleteIndex != null) {
            addedSorList.removeAt(sorcodeToDeleteIndex!!)
            sorcodeToDeleteIndex = null

        }
        updateListofAddedSorUI(addedSorList)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    private fun updateListofAddedSorUI(addedSorList: MutableList<SurveySORs>) =
        viewModelScope.launch {
            Log.i("SystemCheck", "last point")
            var list = mutableListOf<String>()
            for (surveySor in addedSorList) {
                list.add(surveySor.sorCode)
            }
            addedSors = list


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