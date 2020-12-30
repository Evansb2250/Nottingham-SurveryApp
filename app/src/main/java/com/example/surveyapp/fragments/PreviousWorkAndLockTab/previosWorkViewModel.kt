package com.example.surveyapp.fragments.PreviousWorkAndLockTab

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.domains.SoR
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch

class previosWorkViewModel(private val repository: DatabaseRepository) : ViewModel() {

    /*
    PASSED
     */
    val previousWorkData = arrayOf(
        SurveySORs(
            constant.QUESTION1_SOR,
            "HR",
            1,
            "Daywork Labour - Electrician",
            "",
            false,
            0.0,
            0.0
        ), // 1
        SurveySORs(
            constant.QUESTION2_SOR,
            "No",
            1,
            "Disconnect electric cooker.",
            "",
            false,
            0.0,
            0.0
        ), // 2
        SurveySORs(
            constant.QUESTION3_SOR,
            "No",
            1,
            "De-commission intruder alarm",
            "",
            false,
            0.0,
            0.0
        ), // 3
        SurveySORs(
            constant.QUESTION4_SOR,
            "Item",
            1,
            "Supply and Fit mains isolator/PME",
            "",
            false,
            0.0,
            0.0
        ), // 4
        SurveySORs(
            constant.QUESTION5_SOR,
            "No",
            1,
            "Fit insurance lock<",
            "",
            false,
            0.0,
            0.0
        ), // 5
        SurveySORs(
            constant.QUESTION6_SOR,
            "No",
            1,
            "Renew cylinder lock",
            "",
            false,
            0.0,
            0.0
        ), // 6
        SurveySORs(constant.QUESTION7_SOR, "No", 1, "Fix only lock", "", false, 0.0, 0.0), // 7
        SurveySORs(
            constant.QUESTION8_SOR,
            "No",
            1,
            "Double glazed unit up to 0.50m2",
            "",
            false,
            0.0,
            0.0
        ), // 8
        SurveySORs(
            constant.QUESTION9_SOR,
            "No",
            1,
            "Double glazed unit up to 1.0m2",
            "",
            false,
            0.0,
            0.0
        ), // 9
        SurveySORs(
            constant.QUESTION10_SOR,
            "Item",
            1,
            "Periodic inspection in void properties",
            "",
            false,
            0.0,
            0.0
        ), // 10
        SurveySORs(
            constant.QUESTION11_SOR,
            "Item",
            1,
            "installation certification- in conjunction",
            "",
            false,
            0.0,
            0.0
        ), // 11
        SurveySORs(
            constant.QUESTION12_SOR,
            "Item",
            1,
            "Smoke alarm certificates",
            "",
            false,
            0.0,
            0.0
        ), // 12
        SurveySORs(
            constant.QUESTION13_SOR,
            "Item",
            1,
            "Check, test and certificate eletrics before repairs",
            "",
            false,
            0.0,
            0.0
        ), // 13
        SurveySORs(
            constant.QUESTION14_SOR,
            "Item",
            1,
            "Check, test and certificate eletrics after repairs",
            "",
            false,
            0.0,
            0.0
        ), // 14
        SurveySORs(
            constant.QUESTION15_SOR,
            "No",
            1,
            "Additional radial circuit",
            "",
            false,
            0.0,
            0.0
        ), // 15
        SurveySORs(
            constant.QUESTION16_SOR,
            "No",
            1,
            "Additional lighting circuit",
            "",
            false,
            0.0,
            0.0
        ),
        SurveySORs(constant.QUESTION17_SOR, "No", 1, "Additional ring main", "", false, 0.0, 0.0)
    ) // 16


    fun returnPreviosWorkData(): List<SurveySORs> {
        return previousWorkData.toList()
    }

    private var currentSor: SoR? = null
    private var requestRecieved: Boolean? = null

    /*
    PASSED
     */
    fun addChangesToVariable(id: Int, quantity: Double, isRecharge: Boolean, comment: String) {

        when (id) {
            1 -> get(constant.QUESTION1_SOR, id, quantity, isRecharge, comment)
            5 -> get(constant.QUESTION5_SOR, id, quantity, isRecharge, comment)
            6 -> get(constant.QUESTION6_SOR, id, quantity, isRecharge, comment)
            7 -> get(constant.QUESTION7_SOR, id, quantity, isRecharge, comment)
            8 -> get(constant.QUESTION8_SOR, id, quantity, isRecharge, comment)
            9 -> get(constant.QUESTION9_SOR, id, quantity, isRecharge, comment)
            10 -> get(constant.QUESTION10_SOR, id, quantity, isRecharge, comment)
            11 -> get(constant.QUESTION11_SOR, id, quantity, isRecharge, comment)
            12 -> get(constant.QUESTION12_SOR, id, quantity, isRecharge, comment)
            13 -> get(constant.QUESTION13_SOR, id, quantity, isRecharge, comment)
            14 -> get(constant.QUESTION14_SOR, id, quantity, isRecharge, comment)
            15 -> get(constant.QUESTION15_SOR, id, quantity, isRecharge, comment)
            16 -> get(constant.QUESTION16_SOR, id, quantity, isRecharge, comment)
            17 -> get(constant.QUESTION17_SOR, id, quantity, isRecharge, comment)
        }

    }


    /*
    PASSED
     */
    fun addChangetoCheckedVariables(
        id: Int,
        quantity: Double,
        isRecharge: Boolean,
        comment: String
    ) {


        when (id) {
            2 -> get(constant.QUESTION2_SOR, id, quantity, isRecharge, comment)

            3 -> get(constant.QUESTION3_SOR, id, quantity, isRecharge, comment)
            4 -> get(constant.QUESTION4_SOR, id, quantity, isRecharge, comment)
        }

    }


    /*
PASSED
 */
    private fun changeSurveyVariables(
        id: Int,
        quantity: Double,
        isRecharge: Boolean,
        comment: String
    ) {

        previousWorkData.get(id - 1).isRecharge = isRecharge
        previousWorkData.get(id - 1).quantity = quantity
        previousWorkData.get(id - 1).surveyorDescription = comment
        previousWorkData.get(id - 1).total = currentSor!!.rechargeRate * quantity

        Log.i("SystemMessage", previousWorkData.get(id - 1).toString())


    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun get(sorCode: String, id: Int, quantity: Double, isRecharge: Boolean, comment: String) =
        viewModelScope.launch {
            currentSor = repository.getSor(sorCode)

            if (currentSor != null) {
                changeSurveyVariables(id, quantity, isRecharge, comment)
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