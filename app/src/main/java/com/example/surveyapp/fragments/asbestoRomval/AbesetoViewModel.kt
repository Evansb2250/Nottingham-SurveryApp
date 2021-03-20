package com.example.surveyapp.fragments.asbestoRomval

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surveyapp.repository.DatabaseRepository

class AbestoViewModel(private val repository: DatabaseRepository) : ViewModel() {

    private var abestoRemovalDescription:String = ""
     var changeDetected = MutableLiveData<Boolean>()

    fun restoreOldText(oldText: String){
        abestoRemovalDescription = oldText
        changeDetected.value = true
    }

    fun updateText(newText:String){
        abestoRemovalDescription = newText
    }

    fun getAbesto():String{
        return abestoRemovalDescription
    }


}




class AbestoViewModelFactory(private val repository: DatabaseRepository):
    ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AbestoViewModel::class.java)) {

            return AbestoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}