package com.example.surveyapp.fragments.PreviousWorkAndLockTab

import android.util.Log
import androidx.annotation.WorkerThread
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

    /*
    PASSED
     */
    val previousWorkData = arrayOf(
        SurveySORs(constant.QUESTION1_SOR, 1, "", false, 0.0, 0.0), // 1
        SurveySORs(constant.QUESTION2_SOR, 1, "", false, 0.0, 0.0), // 2
        SurveySORs(constant.QUESTION3_SOR, 1, "", false, 0.0, 0.0), // 3
        SurveySORs(constant.QUESTION4_SOR, 1, "", false, 0.0, 0.0), // 4
        SurveySORs(constant.QUESTION5_SOR, 1, "", false, 0.0, 0.0), // 5
        SurveySORs(constant.QUESTION6_SOR, 1, "", false, 0.0, 0.0), // 6
        SurveySORs(constant.QUESTION7_SOR, 1, "", false, 0.0, 0.0), // 7
        SurveySORs(constant.QUESTION8_SOR, 1, "", false, 0.0, 0.0), // 8
        SurveySORs(constant.QUESTION9_SOR, 1, "", false, 0.0, 0.0), // 9
        SurveySORs(constant.QUESTION10_SOR, 1, "", false, 0.0, 0.0), // 10
        SurveySORs(constant.QUESTION11_SOR, 1, "", false, 0.0, 0.0), // 11
        SurveySORs(constant.QUESTION12_SOR, 1, "", false, 0.0, 0.0), // 12
        SurveySORs(constant.QUESTION13_SOR, 1, "", false, 0.0, 0.0), // 13
        SurveySORs(constant.QUESTION14_SOR, 1, "", false, 0.0, 0.0), // 14
        SurveySORs(constant.QUESTION15_SOR, 1, "", false, 0.0, 0.0), // 15
        SurveySORs(constant.QUESTION16_SOR, 1, "", false, 0.0, 0.0),
    ) // 16


    fun returnPreviosWorkData(): List<SurveySORs> {
        return previousWorkData.toList()
    }

    private var currentSor: SoR? = null
    private var requestRecieved: Boolean? = null

    /*
    PASSED
     */
    fun addChangesToVariable(id: Int, quantity: Double, isRecharge: Boolean) {

        when (id) {
            1 -> get(constant.QUESTION1_SOR, id, quantity, isRecharge)
            5 -> get(constant.QUESTION5_SOR, id, quantity, isRecharge)
            6 -> get(constant.QUESTION6_SOR, id, quantity, isRecharge)
            7 -> get(constant.QUESTION7_SOR, id, quantity, isRecharge)
            8 -> get(constant.QUESTION8_SOR, id, quantity, isRecharge)
            9 -> get(constant.QUESTION9_SOR, id, quantity, isRecharge)
            10 -> get(constant.QUESTION10_SOR, id, quantity, isRecharge)
            11 -> get(constant.QUESTION11_SOR, id, quantity, isRecharge)
            12 -> get(constant.QUESTION12_SOR, id, quantity, isRecharge)
            13 -> get(constant.QUESTION13_SOR, id, quantity, isRecharge)
            14 -> get(constant.QUESTION14_SOR, id, quantity, isRecharge)
            15 -> get(constant.QUESTION15_SOR, id, quantity, isRecharge)
            16 -> get(constant.QUESTION16_SOR, id, quantity, isRecharge)
        }

    }


    /*
    PASSED
     */
    fun addChangetoCheckedVariables(id: Int, quantity: Double, isRecharge: Boolean) {

        when (id) {
            2 -> get(constant.QUESTION2_SOR, id, quantity, isRecharge)

            3 -> get(constant.QUESTION3_SOR, id, quantity, isRecharge)
            4 -> get(constant.QUESTION4_SOR, id, quantity, isRecharge)
        }

    }


    /*
PASSED
 */
    private fun changeSurveyVariables(id: Int, quantity: Double, isRecharge: Boolean) {

        previousWorkData.get(id - 1).isRecharge = isRecharge
        previousWorkData.get(id - 1).quantity = quantity
        previousWorkData.get(id - 1).total = currentSor!!.rechargeRate * quantity

        Log.i("SystemMessage", previousWorkData.get(id - 1).toString())


    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun get(sorCode: String, id: Int, quantity: Double, isRecharge: Boolean) =
        viewModelScope.launch {
            currentSor = repository.getSor(sorCode)

            if (currentSor != null) {
                changeSurveyVariables(id, quantity, isRecharge)
                requestRecieved = true

            }
            requestRecieved = false

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

            return previosWorkViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}