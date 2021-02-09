package com.example.surveyapp.fragments.createTab

import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.example.surveyapp.fragments.PreviousWorkAndLockTab.previosWorkViewModel
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch

class createSurveyViewModel(private val repository: DatabaseRepository) : ViewModel() {
    private var surveyName = ""
    private var address = ""
    private var postCode = ""
    private var phoneNumber = ""
    private var date = ""
    private var surveyType = ""
    private var _id :Int = 0


    fun getData(): String {
        return surveyName + "\n" + address + "\n" + postCode + "\n" + phoneNumber + "\n" + date + "\n" + surveyType + "\n"
    }


    fun storeName(newName: String) {
        if (newName != null) {
            surveyName = newName
        } else
            surveyName = ""
    }

    fun storeAddress(newAddress: String) {
        if (newAddress != null) {
            address = newAddress
        } else
            address = ""
    }

    fun storePostCode(newPostCode: String) {
        if (newPostCode != null) {
            postCode = newPostCode
        } else
            postCode = ""
    }

    fun storePhoneNumber(newPhoneNumber: String) {
        if (newPhoneNumber != null) {
            phoneNumber = newPhoneNumber
        }
    }

    fun storeDate(newDate: String) {
        if (newDate != null) {
            date = newDate
        }
    }

    fun setSurveryType(type: String) {
        surveyType = type
    }

    fun getAddress(): String {
        return address
    }

    fun getName(): String {
        return surveyName
    }

    fun getSurveyType(): String {
        return surveyType
    }

    fun getPostCode(): String {
        return postCode
    }

    fun getPhoneNumber(): String {
        return phoneNumber
    }

    fun getDate(): String {
        return date
    }

    fun pullData() {
        TODO("Not yet implemented")
    }
}


class createSurveyViewModelFactory(private val repository: DatabaseRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(createSurveyViewModel::class.java)) {

            return createSurveyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}