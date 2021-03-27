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
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch

class previosWorkViewModel(private val repository: DatabaseRepository) : ViewModel() {


    private var surveyID = 0
    var changeDetect = MutableLiveData<Boolean>()



    fun setSurveyID(id: Int){
        for(x in 0.. previousWorkData.size -1){
            previousWorkData[x].surveyID = id
        }
    }


    /*
    PASSED
     */
    var previousWorkData = arrayListOf<SurveySORs>(
        SurveySORs(
            surveyID,
            constant.QUESTION1_SOR,
            "HR",
            "Daywork Labour - Electrician",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ), // 1
        SurveySORs(
            surveyID,
            constant.QUESTION2_SOR,
            "No",
            "Disconnect electric cooker.",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ), // 2
        SurveySORs(
            surveyID,
            constant.QUESTION3_SOR,
            "No",
            "De-commission intruder alarm",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ), // 3
        SurveySORs(
            surveyID,
            constant.QUESTION4_SOR,
            "Item",
            "Supply and Fit mains isolator/PME",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ), // 4
        SurveySORs(
            surveyID,
            constant.QUESTION5_SOR,
            "No",
            "Fit insurance lock<",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ), // 5
        SurveySORs(
            surveyID,
            constant.QUESTION6_SOR,
            "No",
            "Renew cylinder lock",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ), // 6
        SurveySORs(
            surveyID, constant.QUESTION7_SOR, "No",  "Fix only lock", "", false, 0.0, 0.0,
            constant.STANDARDCODE
        ), // 7
        SurveySORs(
            surveyID,
            constant.QUESTION8_SOR,
            "No",
            "Double glazed unit up to 0.50m2",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ), // 8
        SurveySORs(
            surveyID,
            constant.QUESTION9_SOR,
            "No",
            "Double glazed unit up to 1.0m2",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ), // 9
        SurveySORs(
            surveyID,
            constant.QUESTION10_SOR,
            "Item",

            "Periodic inspection in void properties",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ), // 10
        SurveySORs(
            surveyID,
            constant.QUESTION11_SOR,
            "Item",
            "installation certification- in conjunction",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ), // 11
        SurveySORs(
            surveyID,
            constant.QUESTION12_SOR,
            "Item",
            "Smoke alarm certificates",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ), // 12
        SurveySORs(
            surveyID,
            constant.QUESTION13_SOR,
            "Item",
            "Check, test and certificate eletrics before repairs",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ), // 13
        SurveySORs(
            surveyID,
            constant.QUESTION14_SOR,
            "Item",
            "Check, test and certificate eletrics after repairs",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ), // 14
        SurveySORs(
            surveyID,
            constant.QUESTION15_SOR,
            "No",
                  "Additional radial circuit",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ), // 15
        SurveySORs(
            surveyID,
            constant.QUESTION16_SOR,
            "No",
            "Additional lighting circuit",
            "",
            false,
            0.0,
            0.0,
            constant.STANDARDCODE
        ),
        SurveySORs(
            surveyID, constant.QUESTION17_SOR, "No", "Additional ring main", "", false, 0.0, 0.0,
            constant.STANDARDCODE
        )
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
        previousWorkData.get(id - 1).total = currentSor!!.rechargeRate


    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun get(sorCode: String, id: Int, quantity: Double, isRecharge: Boolean, comment: String) =
        viewModelScope.launch {
            currentSor = repository.getSor(sorCode.trim())
            if (currentSor != null) {
                changeSurveyVariables(id, quantity, isRecharge, comment)
                requestRecieved = true

            }
            requestRecieved = false

        }





    fun loadOldSors(existingSurveySors: List<SurveySORs>){
        if(previousWorkData.size == existingSurveySors.size) {
            previousWorkData = existingSurveySors as ArrayList<SurveySORs>
            changeDetect.value = true
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

            return previosWorkViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}