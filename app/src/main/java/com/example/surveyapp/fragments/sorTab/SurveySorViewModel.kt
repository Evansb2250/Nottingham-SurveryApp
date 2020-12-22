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
    //Individual Sor
    private var currentSor: SoR? = null

    var searchViewEntry: String = ""


    var addedSorList = arrayListOf<SurveySORs>()
    lateinit var addedSors: MutableLiveData<List<SurveySORs>>


    lateinit var viewList: MutableLiveData<List<String>>

    //
    var listForView = mutableListOf<String>()


    //indicates if the search was successful
    var searchWasFound: Boolean



    //Stores and tracks the recharge amount
    val rechargeAmount: LiveData<Double> get() = _rechargeAmount
    val _rechargeAmount = MutableLiveData<Double>()


    //Store the total for the Sor
    val total = MutableLiveData<Double>()

    //
    val quantitySelected = MutableLiveData<Int>()


    // string of SOR
    val searchResult: LiveData<Array<String>> get() = _searchResult
    private val _searchResult = MutableLiveData<Array<String>>()

    // String Description of the SOR
    val sorDescripition: LiveData<String> get() = _sorDescripition
    private val _sorDescripition = MutableLiveData<String>()

    //Value of spinner object for searching
    var searchby = MutableLiveData<String>()

    init {
        //TODO implement this portion after description
        _searchResult.value = null


        searchby.value = constant.SORCODE
        searchWasFound = true
        _sorDescripition.value = ""
        _rechargeAmount.value = 0.0
        quantitySelected.value = 0
        total.value = 0.0
        viewList = MutableLiveData(listForView)

    }


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


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun get(sorCode: String) = viewModelScope.launch {
        currentSor = repository.getSor(sorCode)
        if (currentSor != null) {
            updateCurrentSoR()
        } else
            alertSuccess(false)
    }

    private fun updateCurrentSoR() {
        _sorDescripition.value = currentSor?.description
        _rechargeAmount.value = currentSor?.rechargeRate
        total.value = 0.0
        alertSuccess(true)
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


    fun alertSuccess(result: Boolean) {
        searchWasFound = result
    }


    fun addSorToSurvey(quantity: Int) {
        val sorcode = currentSor!!.sorCode

        // val newSor = SurveySORs()
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