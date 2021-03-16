package com.example.surveyapp.fragments.confirmationTab

import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.ignore.Survey
import com.example.surveyapp.repository.DatabaseRepository

import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.DecimalFormat
import kotlin.collections.ArrayList

class ConfirmViewModel(private val repository: DatabaseRepository) : ViewModel() {

    //object that will create PDF
    private val pdf = PdfCreator(this)
    private val currency = DecimalFormat("£###,###.##")
    var surveyIDTextView = MutableLiveData<Int>()
    var _dataFromSurvey = mutableListOf<SurveySORs>()
    var refreshFinished = MutableLiveData<Boolean>()
    var changes = MutableLiveData<Int>()
    var total = MutableLiveData<Double>()
    var rechargeTotal = MutableLiveData<Double>()
    var VAT = MutableLiveData<Double>()
    var message = MutableLiveData<String>()
    lateinit var surveyInfo: Survey


    var statusMessage = MutableLiveData<String>()
    var hasConditionsBeenMet:Boolean =true
    var canSuveryBeSaved = MutableLiveData<Boolean>()


    private lateinit var dataFromSor: List<SurveySORs>
    private lateinit var dataFromPrev: List<SurveySORs>
    private lateinit var dataFromChecklist: List<SurveySORs>

    lateinit var address: String
    lateinit var name: String
    lateinit var postCode: String
    lateinit var phoneNumber: String
    lateinit var surveyType: String
    var day: Int? = null
    var month: Int? = null
    var year: Int? = null



    //Hides prices
    var hidePrices = false


    init {

        refreshFinished.value = false
        total.value = 0.0
        message.value = ""
        VAT.value = .2
        rechargeTotal.value = 0.0
        changes.value = 0
    }





    //TODO Debate if you need to turn this function into a coroutine
    //  fun getData(): List<SurveySORs> {
    fun getData() = viewModelScope.launch {
        //Default setting
        hasConditionsBeenMet = true

        createSurveyObject()


        if (hasConditionsBeenMet == true) {
            updateConditionsMetStatus(true,"Data Collection Successful")
            dataFromSor = SurveyActivity.sorViewModel?.returnListSORLIST()!!
            dataFromPrev = SurveyActivity.prevViewModel?.returnPreviosWorkData()!!
            dataFromChecklist = SurveyActivity.checkListVM?.getHeatingType()!!

            //    return combineData()
            _dataFromSurvey = combineData() as MutableList<SurveySORs>
            refreshFinished.value = true;

        }




    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun combineData(): List<SurveySORs> {
        val tempList = mutableListOf<SurveySORs>()
        if (dataFromPrev != null) {
            for (data in dataFromPrev) {
                tempList.add(data)
            }
        }

        if (dataFromChecklist != null) {
            for (data in dataFromChecklist) {
                tempList.add(data)
            }
        }
        if (dataFromSor != null) {
            for (data in dataFromSor) {
                tempList.add(data)
            }
        }

        return tempList
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    private suspend fun createSurveyObject() {
        val cantUpdate = "Cant update "

        address = SurveyActivity.createSurveyPage?.getAddress() ?:""
        name = SurveyActivity.createSurveyPage?.getName() ?: ""
        postCode = SurveyActivity.createSurveyPage?.getPostCode() ?: ""
        phoneNumber = SurveyActivity.createSurveyPage?.getPhoneNumber() ?: ""
        surveyType = SurveyActivity.createSurveyPage?.getSurveyType() ?: ""
        day = SurveyActivity.createSurveyPage?.getDay()
        month = SurveyActivity.createSurveyPage?.getMonth()
        year = SurveyActivity.createSurveyPage?.getYear()



        if(name.length > 1 && address.length > 1 && postCode.length > 1){
            if (day != null && month != null && year != null && surveyType.length > 1) {
                surveyInfo = Survey(
                    address = address,
                    postCode = postCode,
                    phoneNumber = phoneNumber,
                    day = day!!,
                    month = month!!,
                    year = year!!,
                    surveyType = surveyType,
                    surveyorName = name,
                    abestoRemovalDescription = ""
                )

            } else {updateConditionsMetStatus(false, "${cantUpdate}Please Complete The Survey Detail Tab!")}


        }else {updateConditionsMetStatus(false, "${cantUpdate}Missing details {Surveyor name, address, or post code }")}



    }


    /*** OG CODE **/
    //  fun updateTotal(List: List<SurveySORs>) {
    fun updateTotal() = viewModelScope.launch {
        computeOnBackEnd()
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    private suspend fun computeOnBackEnd() {

        total.value = 0.0
        rechargeTotal.value = 0.0

        if (_dataFromSurvey != null) {
            //Reseto
            //TODO CREATE AN IF STATEMENT TO ADD TOTAL OR TO JUST UPDATE MESSAGE
            if (hidePrices.equals(false)) {
                for (data in _dataFromSurvey) {
                    total.value = total.value?.plus(data.total)
                    if (data.isRecharge.equals(true)) {
                        rechargeTotal.value = rechargeTotal.value?.plus(data.total)
                    }
                }

                //Apply Vat to recharge amount
                rechargeTotal.value = rechargeTotal.value!! * VAT.value!! + rechargeTotal.value!!
            }


            //Runs update on back thread

            updateMessageOnBackThread()

        }

    }


    fun getSORS(): List<SurveySORs> {
        return _dataFromSurvey
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun updateMessageOnBackThread() = viewModelScope.launch {
        //   messageList = updateMessage(_dataFromSurvey) as ArrayList<String>
        updateMessage(_dataFromSurvey)
    }


    private suspend fun updateMessage(List: List<SurveySORs>) {
        if (List != null) {
            var tempString = ""
            //Reset

            var count = 0

            for (data in List) {
                count += 1
                //TODO add this to a separate return function
                if (hidePrices.equals(false)) {
                    tempString += count.toString() + ". " + data.roomCategory + " |" + data.sorCode + " - " + data.sorDescription + " -  " + currency.format(
                        data.total
                    ) + "\n"
                } else
                    tempString += count.toString() + ". " + data.roomCategory + " |" + data.sorCode + " - " + data.sorDescription + "\n"

            }


            message.value = tempString
        }


    }

// Notifies the GUI side if the operation was successful, and if not what the problem was.
    private fun updateConditionsMetStatus(status: Boolean, message: String) {
    statusMessage.value = message
    hasConditionsBeenMet = status


    }


    fun insertCompleteSurvey() = viewModelScope.launch {

            if (hasConditionsBeenMet == true) {
                addSurvey()
                addSurveySorsList()
                //Sets flag, the activity will then be closed after saving the data
                canSuveryBeSaved.value = true
            } else
                updateConditionsMetStatus(false, "Survey cannot be saved Conditions were not met, make sure all the survey is completed")


    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    private suspend fun addSurveySorsList() {
        for (sor in _dataFromSurvey) {
            repository.insertSurveySors(sor)
        }
    }


    private suspend fun addSurvey() {
        repository.insertCompleteSurvey(surveyInfo)
    }


    fun changeVAT(newVat: Double) {
        VAT.value = newVat / 100
    }


    fun changeShowPriceStatus(hide: Boolean) {
        hidePrices = hide
    }

    fun getCheckedStatus(): Boolean {
        return hidePrices
    }


    fun savePdfHandler() = viewModelScope.launch {
        pdf.savePdf()
    }


}


class confirmViewModelFactory(private val repository: DatabaseRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfirmViewModel::class.java)) {

            return ConfirmViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}