package com.example.surveyapp.fragments.createTab

import androidx.lifecycle.*
import com.example.surveyapp.classLoader.createPagePackage
import com.example.surveyapp.repository.DatabaseRepository

class CreateSurveyViewModel(private val repository: DatabaseRepository) : ViewModel() {

    var day_ = MutableLiveData<Int>()
    var month_ = MutableLiveData<Int>()
    var year_ = MutableLiveData<Int>()


    var surveyName = ""
    var address = ""
    var postCode = ""
    var phoneNumber = ""
    var surveyType = ""



    fun getDay(): Int? {
        return day_.value
    }

    fun getMonth(): Int? {
        return month_.value
    }

    fun getYear(): Int? {
        return year_.value
    }


    fun addSurveyPackage(returnCreatePagePackage: createPagePackage) {

        surveyName = returnCreatePagePackage.name
        address  = returnCreatePagePackage.address
        postCode = returnCreatePagePackage.postCode
        phoneNumber = returnCreatePagePackage.phoneNumber

        //This order is needed since observer pattern is focused on day
        //otherwise month and year will be come null
        month_.value = returnCreatePagePackage.month
        year_.value = returnCreatePagePackage.year
        day_.value = returnCreatePagePackage.day

        surveyType = returnCreatePagePackage.surveyType
        surveyType = returnCreatePagePackage.surveyType

    }


}


class createSurveyViewModelFactory(private val repository: DatabaseRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateSurveyViewModel::class.java)) {

            return CreateSurveyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}