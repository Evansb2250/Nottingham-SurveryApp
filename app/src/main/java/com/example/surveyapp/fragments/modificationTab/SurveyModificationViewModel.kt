package com.example.surveyapp.fragments.modificationTab

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.domains.SoR
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class SurveyModificationViewModel(private val repository: DatabaseRepository) : ViewModel() {

    //currentSor Code
    var loadedSorCode = MutableLiveData<SoR>()

    //error message
    var messageForUser =""

    var errorDetected = MutableLiveData<Boolean>()





    fun checkUpDateRequest(description:String, uom:String, rechargeRate:String){
        try{
            if(description.length <1 || uom.length < 1){
                messageForUser = "Please enter updated text to change description or uom"
                errorDetected.value = true;
            }else{
                val newRechargeRate = rechargeRate.toDouble()

                updateLoadedSoRCode(description, uom, newRechargeRate)
            }

        }catch(e: NumberFormatException){
            createErrorMessage("Recharge rate can only be numbers ex( 15.00 )")
        }
    }



    private fun updateLoadedSoRCode(description: String, uom: String, newRechargeRate: Double) {
        if(loadedSorCode.value != null){
            loadedSorCode.value!!.description = description
            loadedSorCode.value!!.UOM = uom
            loadedSorCode.value!!.rechargeRate = newRechargeRate
            updateSoRCode()
            createGenericMessage("SoR Code ${loadedSorCode.value!!.sorCode} has been edited!")

        }else{
           createErrorMessage("Unable to update change, no code has been selected")
        }
    }

    private fun createGenericMessage(message: String) {
        messageForUser = message
        errorDetected.value = false
    }


    fun getErrorMessageInApp():String{
        return messageForUser
    }

    fun getGenericMessage():String{
        return messageForUser
    }

    fun querySoRCodeRequestFromDB(code:String){
        if(!code.equals("")){
            Log.i("CHECKs", code)
            requestSoRCode(code.trim())
        }else{
            createErrorMessage("No input was found!")
        }
    }

    private fun requestSoRCode(code: String)= viewModelScope.launch{
      val temp = repository.getSor(code)
        if(temp != null){
            changeLoadedSorCode(temp)
        }else{

          createErrorMessage("SoR code (${code}) was not found in database")
        }
    }

    private fun updateSoRCode() = viewModelScope.launch {
        repository.upDateSoRCode(loadedSorCode.value!!)
    }


    private fun createErrorMessage(error:String){
        messageForUser = error
        errorDetected.value = true
    }


    private fun changeLoadedSorCode(temp: SoR) {
        loadedSorCode.value = temp
    }


    fun setLoadSorToNull() {
        loadedSorCode.value = null
    }


}


class SurveyModificationViewModelFactory(private val repository: DatabaseRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveyModificationViewModel::class.java)) {

            return SurveyModificationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}