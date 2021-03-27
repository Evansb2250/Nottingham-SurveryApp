package com.example.surveyapp.fragments.confirmationTab

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.domains.ChecklistEntries
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.ignore.Survey
import com.example.surveyapp.repository.DatabaseRepository

import kotlinx.coroutines.launch
import java.text.DecimalFormat

class ConfirmViewModel(private val repository: DatabaseRepository) : ViewModel() {

    //object that will create PDF
    private val pdf = PdfCreator(this)
    private val currency = DecimalFormat("£###,###.##")
    var surveyIDTextView = MutableLiveData<Int>()
    var _sorListData = mutableListOf<SurveySORs>()
    var refreshFinished = MutableLiveData<Boolean>()
    var changes = MutableLiveData<Int>()
    var total = MutableLiveData<Double>()
    var rechargeTotal = MutableLiveData<Double>()
    var VAT = MutableLiveData<Double>()
    var message = MutableLiveData<String>()
    lateinit var surveyInfo: Survey


    var statusMessage = MutableLiveData<String>()
    var hasConditionsBeenMet: Boolean = true
    var canSuveryBeSaved = MutableLiveData<Boolean>()


    var updateDetected = MutableLiveData<Boolean>()


    private lateinit var dataFromSor: List<SurveySORs>
    private lateinit var dataFromPrev: List<SurveySORs>
    private lateinit var dataFromChecklist: List<SurveySORs>

    lateinit var address: String
    lateinit var name: String
    lateinit var postCode: String
    lateinit var phoneNumber: String
    lateinit var surveyType: String
    lateinit var abestoText:String
   // var surveyId:Int?=null



    var day: Int? = null
    var month: Int? = null
    var year: Int? = null

    var checkListObject: ChecklistEntries? = null


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
        createChecklistObject()

        if (hasConditionsBeenMet == true) {
            updateConditionsMetStatus(true, "Data Collection Successful")
            dataFromSor = SurveyActivity.sorViewModel?.returnListSORLIST()!!
            dataFromPrev = SurveyActivity.prevViewModel?.returnPreviosWorkData()!!

            dataFromChecklist = SurveyActivity.checkListVM?.getHeatingType()!!

            //    return combineData()
            _sorListData = combineData() as MutableList<SurveySORs>
            refreshFinished.value = true;

        }


    }


    private suspend fun createChecklistObject() {
        checkListObject = SurveyActivity?.checkListVM?.getCheckListObject()
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
            for (data in dataFromSor.asReversed()) {
                tempList.add(data)
            }
        }


        return tempList
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    private suspend fun createSurveyObject() {
        initializeSurveyInfoVariables()
        val isSurveyDetailsCompleted = surveyTabScanResults()
        if(isSurveyDetailsCompleted){
            createSurveyDomain()
        }

    }

    private fun createSurveyDomain() {
        surveyInfo = Survey(
            surveyId = surveyIDTextView.value!!,
            address = address,
            postCode = postCode,
            phoneNumber = phoneNumber,
            day = day!!,
            month = month!!,
            year = year!!,
            surveyType = surveyType,
            surveyorName = name,
            abestoRemovalDescription = abestoText,
            surveyTotal = 0.0,
            rechargeTotal = 0.0
        )
        //SURVEY IS AUTOMATICALLY INCREMENTEND

    }



    fun changeVATDEFAULT(vat: Double){
        VAT.value = vat
        updateDetected.value = true

    }






    private fun surveyTabScanResults(): Boolean {
        val cantUpdate = "Cant update "
        //Checks if the user added (Name, addres, and PostCode Details
        if (name.length > 1 && address.length > 1  && postCode.length > 1) {
            //Checks if the user selected a date for the survey
            if (day != null && month != null && year != null && surveyType.length > 1) {
                //Returns true only if these requirements are made
                return true
            } else {
                //Generates an  error message
                updateConditionsMetStatus(false, "${cantUpdate}Please Complete The Survey Detail Tab!") }
        } else {
            //Generates an  error message
            updateConditionsMetStatus(false, "${cantUpdate}Missing details {Surveyor name, address, or post code }")
        }
        return false
    }


    private fun initializeSurveyInfoVariables() {
        address = SurveyActivity.createSurveyPage?.getAddress() ?: ""
        name = SurveyActivity.createSurveyPage?.getName() ?: ""
        postCode = SurveyActivity.createSurveyPage?.getPostCode() ?: ""
        phoneNumber = SurveyActivity.createSurveyPage?.getPhoneNumber() ?: ""
        surveyType = SurveyActivity.createSurveyPage?.getSurveyType() ?: ""
        day = SurveyActivity.createSurveyPage?.getDay()
        month = SurveyActivity.createSurveyPage?.getMonth()
        year = SurveyActivity.createSurveyPage?.getYear()
        abestoText = SurveyActivity?.abesto?.getAbesto().toString()

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

        if (_sorListData != null) {
            //Reseto
            //TODO CREATE AN IF STATEMENT TO ADD TOTAL OR TO JUST UPDATE MESSAGE
            if (hidePrices.equals(false)) {
                for (data in _sorListData) {
                    total.value = total.value?.plus(data.total * data.quantity) //TODO just added data.quantity
                    if (data.isRecharge.equals(true)) {
                        rechargeTotal.value = rechargeTotal.value?.plus(data.total * data.quantity)
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
        return _sorListData
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun updateMessageOnBackThread() = viewModelScope.launch {
        //   messageList = updateMessage(_dataFromSurvey) as ArrayList<String>
        updateMessage(_sorListData)
    }


    private suspend fun updateMessage(List: List<SurveySORs>) {
        if (List != null) {
            var tempString = ""
            //Reset

            var count = 0

            for (data in List.asReversed()) {
                count += 1
                //TODO add this to a separate return function
                if (hidePrices.equals(false)) {
                    tempString += count.toString() + ". " + data.roomCategory + " |" + data.sorCode + " - " + data.sorDescription + " -  " + currency.format(
                        data.total * data.quantity
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

        if(surveyInfo != null){
            //adds the survey total inside the survey object
            surveyInfo.surveyTotal = total.value?:0.0
            //adds the recharge amount into the survey object
            surveyInfo.rechargeTotal = rechargeTotal.value ?:0.0
            surveyInfo.vatAmount = VAT.value!!
        }


        if (hasConditionsBeenMet == true) {
            addSurvey()
            //Sets flag, the activity will then be closed after saving the data
            canSuveryBeSaved.value = true
        } else
            updateConditionsMetStatus(
                false,
                "Survey cannot be saved Conditions were not met, make sure all the survey is completed"
            )


    }


    private suspend fun addSurvey() {
       // repository.insertCompleteSurvey(surveyInfo)
        //checkListObject?.surveyId = surveyInfo.surveyId
        repository.insertSurveyPreCheck(surveyInfo,_sorListData,checkListObject!!)
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