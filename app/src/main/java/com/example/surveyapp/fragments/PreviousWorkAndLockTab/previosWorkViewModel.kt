package com.example.surveyapp.fragments.PreviousWorkAndLockTab

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.domains.SoR
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.fragments.sorTab.SurveySorViewModel
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch

class previosWorkViewModel(private val repository: DatabaseRepository) : ViewModel() {


    companion object {
        var test = Array<SurveySORs>(12)
        private fun <T> Array(size: Int) {

        }


        //QUESTION 1
        lateinit var DW0090: SurveySORs

        //QUESTION 2
        lateinit var EN4530: SurveySORs

        //QUESTION 3
        lateinit var EN6160: SurveySORs

        //QUESTION 4
        lateinit var EN1010: SurveySORs

        //QUESTION 5
        lateinit var JN2030: SurveySORs

        //QUESTION 6
        lateinit var JN2070: SurveySORs

        //QUESTION 7
        lateinit var JN2040: SurveySORs

        //QUESTION 8
        lateinit var GR0510: SurveySORs

        //QUESTION 9
        lateinit var GR0520: SurveySORs

        //QUESTION 10
        lateinit var EN0510: SurveySORs

        //QUESTION 11
        lateinit var EN0610: SurveySORs

        //QUESTION 12
        lateinit var EN0650: SurveySORs

        //QUESTION 13
        lateinit var EN0660: SurveySORs

        //QUESTION 14
        lateinit var EN0530: SurveySORs

        //QUESTION 15
        lateinit var EN0540: SurveySORs

        //QUESTION 16
        lateinit var EN0550: SurveySORs

    }


    private var currentSor: SoR? = null
    private var requestRecieved: Boolean? = null


    fun addChangesToVariable(id: Int, quantity: Double, isRecharge: Boolean) {

        when (id) {
            1 -> {
                get(constant.QUESTION1_SOR)

                Log.i(
                    "SystemMessage",
                    "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
                )
            }

            5 -> {
                Log.i(
                    "SystemMessage",
                    "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
                )
            }
            6 -> {

                Log.i(
                    "SystemMessage",
                    "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
                )
            }
            7 -> {

                Log.i(
                    "SystemMessage",
                    "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
                )
            }
            8 -> {

                Log.i(
                    "SystemMessage",
                    "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
                )
            }
            9 -> {

                Log.i(
                    "SystemMessage",
                    "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
                )
            }
            10 -> {

                Log.i(
                    "SystemMessage",
                    "Slot " + id + " quantity " + quantity + " is a recharge " + isRecharge
                )
            }
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


    private fun instantiateVariable(id: Int, quantity: Double, recharge: Boolean) {
        var sorCode = currentSor!!.sorCode
        //TODO CHANGE THIS TO REGISTER THE RIGHT SURVEY ID
        var surveyID = 1
        var surveyorDescription = " "
        var isRecharge = recharge
        var quantity = quantity
        var total = currentSor!!.rechargeRate * quantity

        test.set(1, SurveySORs(sorCode, surveyID, surveyorDescription, isRecharge, quantity, total))

    }

    fun addChangetoCheckedVariables(id: Int, isChecked: Int, isRecharge: Boolean) {

        when (id) {
            2 -> Log.i(
                "SystemMessage",
                "Slot " + id + "  is Checked " + isChecked + " is a recharge " + isRecharge
            )
            3 -> Log.i(
                "SystemMessage",
                "Slot " + id + "  is Checked " + isChecked + " is a recharge " + isRecharge
            )
            4 -> Log.i(
                "SystemMessage",
                "Slot " + id + "  is Checked " + isChecked + " is a recharge " + isRecharge
            )
        }

    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun get(sorCode: String) = viewModelScope.launch {
        currentSor = repository.getSor(sorCode)
        if (currentSor != null) {
            requestRecieved = true

        }
        requestRecieved = false

    }


}

private fun Any.set(i: Int, sor: SurveySORs) {

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