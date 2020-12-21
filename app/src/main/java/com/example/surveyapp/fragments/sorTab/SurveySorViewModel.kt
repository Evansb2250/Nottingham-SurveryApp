package com.example.surveyapp.fragments.sorTab

import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.example.surveyapp.domains.SoR
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch

class SurveySorViewModel(private val repository: DatabaseRepository) : ViewModel() {
    private val SORCODE = "SOR CODE"
    private val KEYWORD = "Keyword"


    val searchResult: LiveData<String> get() = _searchResult
    private val _searchResult = MutableLiveData<String>()

    init {
        _searchResult.value = ""
    }


    fun searchFor(searchOption: String, userInput: String) {
        when (searchOption) {
            SORCODE -> searchBySorCode(userInput)

            KEYWORD -> searchByKeyword(userInput)
        }
    }

    private fun searchByKeyword(userInput: String) {

    }


    private fun searchBySorCode(sorCode: String) {
        get(sorCode)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun get(sorCode: String) = viewModelScope.launch {
        val sor = repository.getSor(sorCode)
        _searchResult.value = sor.description
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