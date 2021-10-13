package com.example.surveyapp.fragments.checkListTab

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.domains.ChecklistEntries
import com.example.surveyapp.domains.SoR
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.launch

class ChecklistViewModel(private val repository: DatabaseRepository) : ViewModel() {

    val uniqueSorCodeNumber = -1000


    companion object {
        private lateinit var Global_heatingType: List<SurveySORs>

    }

    private var _surveyID: Int? = null
    private var heatingType: List<SurveySORs>? = null
    private var sorList = mutableListOf<SoR>()

    private var heatTypeIndex = 0
    private var checkListObject: ChecklistEntries? = null
    private var fireDoorComment = ""
    private var decorationPoints = ""
    private var tapsComment = ""
    private var floorComment = ""


    var changeDetected = MutableLiveData<Boolean>()
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

    var checBoxLiveState_ = checkBoxStatus



    fun setSurveyID(id: Int) {
        _surveyID = id
        updateChecklistObject()

    }




    fun setHeatyingType(code: Int) {
        when (code) {
            0 -> {
                val empytList = mutableListOf<SurveySORs>()
                heatTypeIndex = 0
                updateChecklistObject()
                heatingType = empytList

            }
            1 -> {
                val systemBoilerCodes = constant.BOILERPOINTCODES
                heatTypeIndex = constant.BOILERPOINT_ID
                updateChecklistObject()

                // prevents duplicates
                if (heatingType?.size != 5) {
                    get(systemBoilerCodes)
                }
            }
            2 -> {
                val combiHeating = constant.COMBIBOILER
                heatTypeIndex = constant.COMBIEBOILER_ID
                updateChecklistObject()

                // prevents duplicates
                if (heatingType?.size != 2) {
                    get(combiHeating)
                }

            }
            3 -> {
                val deheating = mutableListOf<String>(constant.DHEATING)
                heatTypeIndex = constant.DHEATING_ID
                updateChecklistObject()

                // prevents duplicates
                if (heatingType?.size != 1) {
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

                if (_surveyID != null) {
                    surveyID = _surveyID!!
                }

                tempList.add(
                    SurveySORs(
                        uniqueSorCodeNumber,
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
            Global_heatingType = heatingType as MutableList<SurveySORs>
        }
    }

    fun requestCheckList(checklistEntries: ChecklistEntries){

        allocateCheckListEntries(constant.FIRE_DOOR_BOX_ID, checklistEntries.fireDoor)
        allocateCheckListEntries(constant.ISOLATOR_BOX_ID,  checklistEntries.isolator)
        allocateCheckListEntries(constant.METER_BOX_ID,  checklistEntries.meterIssue)
        allocateCheckListEntries(constant.REWIRE_BOX_ID, checklistEntries.rewire)
        allocateCheckListEntries(constant.FAST_TRACKING_BOX_ID, checklistEntries.fastTrack)
        allocateCheckListEntries(constant.ASTO_BOX_ID, checklistEntries.altro)
        allocateCheckListEntries(constant.HEATING_BOX_ID, checklistEntries.heating)
        allocateCheckListEntries(constant.GLASS_BOX_ID, checklistEntries.glass)

        upDateTapsComment(checklistEntries.locationTaps)
        upDateFloorComment(checklistEntries.floorLevel)
        updateDecorTaps(checklistEntries.decorationPoints)
        storeFireDoorComment(checklistEntries.fireDoorComment)
        heatTypeIndex = checklistEntries.heatType


        checBoxLiveState_ = checkBoxStatus
        changeDetected.value =true
    }


    private fun allocateCheckListEntries(id: Int, isChecked: Boolean) {
        checkBoxStatus[id] = isChecked
        updateChecklistObject()
    }


    fun registerClick(id: Int, checked: Boolean) {

        when (id) {
            constant.FIRE_DOOR_BOX_ID -> {
                checkBoxStatus[id] = checked
                updateChecklistObject()


            }
            constant.ISOLATOR_BOX_ID -> {
                checkBoxStatus[id] = checked
                updateChecklistObject()

            }
            constant.METER_BOX_ID -> {
                checkBoxStatus[id] = checked
                updateChecklistObject()

            }
            constant.FAST_TRACKING_BOX_ID -> {
                checkBoxStatus[id] = checked
                updateChecklistObject()

            }
            constant.ASTO_BOX_ID -> {
                checkBoxStatus[id] = checked
                updateChecklistObject()

            }
            constant.REWIRE_BOX_ID -> {
                checkBoxStatus[id] = checked
                updateChecklistObject()
            }
            constant.HEATING_BOX_ID -> {
                checkBoxStatus[id] = checked
                updateChecklistObject()
            }
            constant.GLASS_BOX_ID -> {
                checkBoxStatus[id] = checked
                updateChecklistObject()

            }
        }
    }

    fun storeFireDoorComment(newComment: String) {
        fireDoorComment = newComment
        updateChecklistObject()

    }

    fun getFireDoorComments(): String {
        return fireDoorComment
    }

    fun getDecorPoints(): String {
        return decorationPoints
    }


    fun getTapsLocation(): String? {
        return tapsComment;
    }


    fun getFloorLevel(): String {
        return floorComment
    }


    fun updateDecorTaps(decorText: String) {
        decorationPoints = decorText
        updateChecklistObject()


    }

    fun upDateFloorComment(floorText: String) {
        floorComment = floorText
        updateChecklistObject()

    }

    fun upDateTapsComment(tapsText: String) {
        tapsComment = tapsText
        updateChecklistObject()

    }


    fun getHeatTypeIndex(): Int {
        return heatTypeIndex
    }

    fun getMeterStatus(): String {
        return returnStatus(constant.METER_BOX_ID)
    }


    fun getFireDoorStatus(): String {
        return returnStatus(constant.FIRE_DOOR_BOX_ID)
    }

    fun getIsolatorStatus(): String {
        return returnStatus(constant.ISOLATOR_BOX_ID)
    }

    fun getRewireStatus(): String {
        return returnStatus(constant.REWIRE_BOX_ID)
    }

    fun getHouseHeatingStatus(): String {
        return returnStatus(constant.HEATING_BOX_ID)
    }

    fun getFastTrackStatus(): String {
        return returnStatus(constant.FAST_TRACKING_BOX_ID)
    }

    fun getHouseHasGlassStatus(): String {
        return returnStatus(constant.GLASS_BOX_ID)
    }

    fun getAltroStatus(): String {
        return returnStatus(constant.ASTO_BOX_ID)
    }


    private fun returnStatus(id: Int): String {
        if (checkBoxStatus[id] == true) {
            return "YES"
        } else
            return "NO"
    }

    private fun updateChecklistObject() {

        if(_surveyID != null) {
    checkListObject = ChecklistEntries(
        surveyId = _surveyID!!,
        heatType = heatTypeIndex,
        fireDoor = checkBoxStatus[constant.FIRE_DOOR_BOX_ID],
        fireDoorComment = fireDoorComment,
        decorationPoints = decorationPoints,
        locationTaps = tapsComment,
        floorLevel = floorComment,
        isolator = checkBoxStatus[constant.ISOLATOR_BOX_ID],
        meterIssue = checkBoxStatus[constant.METER_BOX_ID],
        fastTrack = checkBoxStatus[constant.FAST_TRACKING_BOX_ID],
        altro = checkBoxStatus[constant.ASTO_BOX_ID],
        rewire = checkBoxStatus[constant.REWIRE_BOX_ID],
        heating = checkBoxStatus[constant.HEATING_BOX_ID],
        glass = checkBoxStatus[constant.GLASS_BOX_ID]
    )
}
    }

    fun getCheckListObject():ChecklistEntries?{
        return checkListObject
    }


    //Functions to get and create a


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