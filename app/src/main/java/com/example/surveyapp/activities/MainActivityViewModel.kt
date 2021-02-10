package com.example.surveyapp.activities

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: DatabaseRepository) : ViewModel() {

    fun refreshPage(){
        refresh()
    }



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun refresh() = viewModelScope.launch {
        repository.getSor("Unknown")
    }

}


class MainActivityViewModelFactory(private val repository: DatabaseRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {

            return MainActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}