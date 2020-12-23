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

    /*
    Static or Companion object variables
     */
    companion object {
        var addedSorList = arrayListOf<SurveySORs>()
    }


    /*****
    Variable for creating a new SoR code attached to the survey
     ****/

    //Store the total for the Sor USED for both the SorViewModel and data in the SurveySORs data class
    val total = MutableLiveData<Double>()

    //Number selected in the number wheel
    //TODO change to a text entry to add floating point numbers
    val quantitySelected = MutableLiveData<Int>()


    /*****
    Variables representing the data appeared on the Fragment and ViewModel
     ****/
    //Individual Sor
    var currentSor: SoR? = null

    //
    var searchViewEntry: String = ""


    lateinit var addedSors: MutableLiveData<List<String>>


    lateinit var viewList: MutableLiveData<List<String>>

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


    // String Description of the SOR
    val sorDescripition: LiveData<String> get() = _sorDescripition
    private val _sorDescripition = MutableLiveData<String>()

    //Value of spinner object for searching
    var searchby = MutableLiveData<String>()

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


    fun addSorToSurvey(quantity: Int) {
        val sorcode = currentSor!!.sorCode


    }


    private fun updateCurrentSoR() {
        _sorDescripition.value = currentSor?.description
        _rechargeAmount.value = currentSor?.rechargeRate
        total.value = 0.0
        alertSuccess(true)
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


}


class SurveySorViewModelFactory(private val repository: DatabaseRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveySorViewModel::class.java)) {

            return SurveySorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}