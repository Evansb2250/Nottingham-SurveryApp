package com.example.surveyapp.fragments.checkListTab

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

class ChecklistViewModel(private val repository: DatabaseRepository) : ViewModel() {




    companion object {
        private lateinit var Global_heatingType: List<SurveySORs>

    }
    private var _surveyID :Int ?= null
    private lateinit var heatingType: List<SurveySORs>
    private var sorList = mutableListOf<SoR>()

    private var fireDoorComment = ""
    private var decorationPoints =""
    private var tapsComment =""
    private var floorComment = ""



    private val checkBoxStatus = arrayListOf(
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false
    )














    fun setSurveyID(id: Int){
        _surveyID = id
    }






    fun getHeatingType(): List<SurveySORs> {
        return heatingType
    }


    fun setHeatyingType(code: Int) {
        when (code) {
            0 -> {
                val empytList = mutableListOf<SurveySORs>()
                heatingType = empytList

            }
            1 -> {
                val systemBoilerCodes = constant.BOILERPOINTCODES
                if (heatingType.size != 5) {
                    get(systemBoilerCodes)
                }
            }
            2 -> {
                val combiHeating = constant.COMBIBOILER
                if (heatingType.size != 2) {
                    get(combiHeating)
                }

            }
            3 -> {
                val deheating = mutableListOf<String>(constant.DHEATING)
                if (heatingType.size != 1) {
                    get(deheating)
                }

            }
        }
    }

    // set up a function with a when statement
    // call the get function with the constant refering to the sor codes in each heating type


    //search each sor
    // add them in a temp list of Sor
    // create a temp list of SurveySors
    // use data in List Sor to instantiate surveySors
    // use pre defined files for each comment and quantity number
    //instantiate heatType with tmp
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    private fun get(sorCodeList: MutableList<String>) =
        viewModelScope.launch {
            // Clear list of previous entries
            sorList.clear()
            for (sorCode in sorCodeList) {
                val currentSor = repository.getSor(sorCode)
                if (currentSor != null) {
                    sorList.add(currentSor)
                }

            }

            createSurveySor()
        }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun createSurveySor() {
        if (sorList != null) {
            val tempList = mutableListOf<SurveySORs>()
            for (sor in sorList) {
                val sorcode = sor.sorCode
                val total = sor.rechargeRate * 1.0
                val description = sor.description
                val comment = ""
                val uom = sor.UOM
                var surveyID = -1

                if(_surveyID != null){
                    surveyID = _surveyID!!
                }

                tempList.add(
                    SurveySORs(
                        surveyID,
                        sorcode,
                        uom,
                        description,
                        comment,
                        false,
                        1.0,
                        total,
                        constant.STANDARDCODE
                    )
                )
            }
            heatingType = tempList
            Global_heatingType = heatingType
        }
    }

    fun registerClick(id: Int, checked: Boolean) {

        when (id) {
            constant.FIRE_DOOR_BOX_ID -> {
                checkBoxStatus[id] = checked
            }
            constant.ISOLATOR_BOX_ID -> {
                checkBoxStatus[id] = checked
            }
            constant.METER_BOX_ID  -> {
                checkBoxStatus[id] = checked
            }
            constant.FAST_TRACKING_BOX_ID  -> {
                checkBoxStatus[id] = checked
            }
            constant.ASTO_BOX_ID -> {
                checkBoxStatus[id] = checked
            }
            constant.REWIRE_BOX_ID  -> {
                checkBoxStatus[id] = checked
            }
            constant.HEATING_BOX_ID  -> {
                checkBoxStatus[id] = checked
            }
            constant.GLASS_BOX_ID  -> {
                checkBoxStatus[id] = checked
            }
        }
    }

    fun storeFireDoorComment(newComment: String) {
        fireDoorComment = newComment
    }

    fun getFireDoorComments():String{
        return fireDoorComment
    }

    fun getDecorPoints():String{
        return decorationPoints
    }


    fun getTapsLocation(): String? {
        return tapsComment;
    }


    fun getFloorLevel():String{
        return floorComment
    }




    fun updateDecorTaps(decorText: String) {
        decorationPoints = decorText

    }

    fun upDateFloorComment(floorText: String) {
        floorComment = floorText
    }

    fun upDateTapsComment(tapsText: String) {
        tapsComment = tapsText
    }




    fun getMeterStatus():String {
        return returnStatus(constant.METER_BOX_ID)
    }


    fun getFireDoorStatus():String{
        return returnStatus(constant.FIRE_DOOR_BOX_ID)
    }

    fun getIsolatorStatus(): String{
        return returnStatus(constant.ISOLATOR_BOX_ID)
    }

    fun getRewireStatus(): String{
        return returnStatus(constant.REWIRE_BOX_ID)
    }

    fun getHouseHeatingStatus():String{
        return returnStatus(constant.HEATING_BOX_ID)
    }

    fun getFastTrackStatus():String{
        return  returnStatus(constant.FAST_TRACKING_BOX_ID)
    }

    fun getHouseHasGlassStatus():String{
        return returnStatus(constant.GLASS_BOX_ID)
    }

    fun getAltroStatus():String{
        return returnStatus(constant.ASTO_BOX_ID)
    }




    private fun returnStatus(id:Int): String{
        if(checkBoxStatus[id] == true){
            return "YES"
        }else
            return "NO"
    }

}



/****
 *
 *  ViewModelFactory
 *
 */

class ChecklistViewModelFactory(private val repository: DatabaseRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChecklistViewModel::class.java)) {

            return ChecklistViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}