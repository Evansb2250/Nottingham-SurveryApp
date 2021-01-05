package com.example.surveyapp.fragments.confirmationTab

import android.util.Log
import androidx.lifecycle.*
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.repository.DatabaseRepository
import java.text.DecimalFormat

class ConfirmViewModel(private val repository: DatabaseRepository) : ViewModel() {

    private val currency = DecimalFormat("£###,###.##")
    private val numberCounter = DecimalFormat("####. ")


    var _dataFromSurvey = mutableListOf<SurveySORs>()


    var total = MutableLiveData<Double>()
    var rechargeTotal = MutableLiveData<Double>()
    var VAT = MutableLiveData<Double>()
    var message = MutableLiveData<String>()
    var messageList = arrayListOf<String>()


    fun getList(): List<String> {
        if (messageList != null) {
            return messageList
        }
        return emptyList()
    }


    init {
        total.value = 0.0
        message.value = ""
        VAT.value = .2
        rechargeTotal.value = 0.0
    }

    private lateinit var dataFromSor: List<SurveySORs>
    private lateinit var dataFromPrev: List<SurveySORs>
    private lateinit var dataFromChecklist: List<SurveySORs>

    private lateinit var address: String
    private lateinit var name: String
    private lateinit var postCode: String
    private lateinit var phoneNumber: String
    private lateinit var surveyType: String
    private lateinit var date: String

    //Separator
    private val sp = "@"



    //Details


    //TODO Debate if you need to turn this function into a coroutine
    //  fun getData(): List<SurveySORs> {
    fun getData() {
        address = SurveyActivity.createSurveyPage?.getAddress() ?: ""
        name = SurveyActivity.createSurveyPage?.getName() ?: " "
        postCode = SurveyActivity.createSurveyPage?.getPostCode() ?: " "
        phoneNumber = SurveyActivity.createSurveyPage?.getPhoneNumber() ?: ""
        surveyType = SurveyActivity.createSurveyPage?.getSurveyType() ?: ""
        date = SurveyActivity.createSurveyPage?.getDate() ?: ""


        dataFromSor = SurveyActivity.sorViewModel?.returnListSORLIST()!!



        dataFromPrev = SurveyActivity.prevViewModel?.returnPreviosWorkData()!!



        dataFromChecklist = SurveyActivity.checkListVM?.getHeatingType()!!

        /** OG CODE **/
        //    return combineData()

        _dataFromSurvey = combineData() as MutableList<SurveySORs>
    }

    fun combineData(): List<SurveySORs> {
        val tempList = mutableListOf<SurveySORs>()

        if (dataFromSor != null) {
            for (data in dataFromSor) {
                tempList.add(data)
            }
        }
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
        //_dataFromSurvey = MutableLiveData(tempList)
        return tempList
    }

    /*** OG CODE **/
    //  fun updateTotal(List: List<SurveySORs>) {
    fun updateTotal() {
        total.value = 0.0
        rechargeTotal.value = 0.0

        if (_dataFromSurvey != null) {
            //Reseto


            for (data in _dataFromSurvey) {
                total.value = total.value?.plus(data.total)
                if (data.isRecharge.equals(true)) {
                    rechargeTotal.value = rechargeTotal.value?.plus(data.total)
                }
            }

            //Apply Vat to recharge amount
            rechargeTotal.value = rechargeTotal.value!! * VAT.value!! + rechargeTotal.value!!

            messageList = updateMessage(_dataFromSurvey) as ArrayList<String>
        }
    }


    fun updateMessage(List: List<SurveySORs>): List<String> {
        val tempList = mutableListOf<String>()
        if (List != null) {
            //Reset
            message.value = ""
            var count = 0
            var capital = "0"
            var revenue = "0"
            var capitalTotal = "0.0"
            var revenueTotal = "0.0"

            //Adding header
            if (surveyType.equals(constant.CAPTIAL)) {
                capital = "y"
                capitalTotal = currency.format(total.value)
            } else if (surveyType.equals(constant.REVENUE)) {
                revenue = "y"
                revenueTotal = currency.format(total.value)
            }


            val header =
                "Address:${sp}${address}${sp}0${sp} ${sp}Survey:${sp}${name}${sp}${sp}${sp}Revenue Void Total${sp}${revenueTotal}\n"
            val header2 =
                "Revenue ${sp} ${revenue} ${sp} Capital${sp} ${capital} ${sp} ${sp} Fire  Service Referral${sp}0 ${sp}${sp} Capital Void Total ${sp}${capitalTotal} + \n"
            var header3 =
                "SoRs${sp}Description${sp}${sp}UOM${sp}QTY${sp}Recharge${sp}Total Price${sp}Comments${sp}Recharge Total${sp}${
                    currency.format(rechargeTotal.value)
                } \n"
            tempList.add(header)
            tempList.add(header2)
            tempList.add(header3)
            for (data in List) {
                count += 1
                message.value += count.toString() + ". " + data.roomCategory + "  " + data.sorCode + " " + currency.format(
                    data.total
                ) + "\n"
                var response = "?"
                if (data.isRecharge.equals(true)) {
                    response = "y"
                } else
                    response = " "
                //TODO add rounding function
                tempList.add(
                    data.roomCategory + sp + data.sorCode + sp + data.sorDescription + sp + sp +
                            data.UOM + sp + data.quantity + sp + response + sp +
                            currency.format(data.total) + sp +
                            data.surveyorDescription + "\n"
                )

            }
            return tempList
        } else
            return tempList

    }

    fun changeVAT(newVat: Double) {
        VAT.value = newVat / 100
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