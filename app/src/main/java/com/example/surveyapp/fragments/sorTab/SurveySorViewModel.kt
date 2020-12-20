package com.example.surveyapp.fragments.sorTab

import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.example.surveyapp.domains.SoR
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch

class SurveySorViewModel(private val repository: DatabaseRepository) : ViewModel() {
//
//    var fakeData : MutableLiveData<String>? = null
//
//    init {
//        fakeData?.value = "dsadsa"
//    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun get(sorCode: String) = viewModelScope.launch {
        repository.getSor(sorCode)

    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(sorCode: SoR) = viewModelScope.launch {
        repository.insert(sorCode)

    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun removeSoR(sorCode: String) = viewModelScope.launch {
        repository.removeFromSoR(sorCode)

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