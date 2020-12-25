package com.example.surveyapp.fragments.PreviousWorkAndLockTab

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surveyapp.fragments.sorTab.SurveySorViewModel
import com.example.surveyapp.repository.DatabaseRepository

class previosWorkViewModel : ViewModel() {


    fun addChangesToVariable(id: Int, quantity: Double, isRecharge: Boolean) {

        when (id) {
            1 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            2 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            3 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            4 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            5 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            6 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            7 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            8 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            9 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            10 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            11 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            12 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            13 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            14 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            15 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
            16 -> Log.i(
                "SystemMessage",
                "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
            )
        }

    }

}


/****
 *
 *  ViewModelFactory
 *
 */

class previosWorkViewModelFactory(private val repository: DatabaseRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(previosWorkViewModel::class.java)) {

            return SurveySorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}