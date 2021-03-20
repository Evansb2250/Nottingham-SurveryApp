package com.example.surveyapp.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.surveyapp.DAO.dbDAO
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.domains.ChecklistEntries
import com.example.surveyapp.domains.SoR
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.ignore.Survey

class DatabaseRepository(private val dbManager: dbDAO) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertSurveySors(sorList: List<SurveySORs>) {
        dbManager.insertSurveySorList(sorList)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertCompleteSurvey(survey: Survey) {
        dbManager.insertSurvey(survey)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addCheckList(checklist:ChecklistEntries){
        dbManager.insertCheckList(checklist)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertSurveyPreCheck(survey: Survey, listOfSors: List<SurveySORs>, checklist: ChecklistEntries){
        //Check if prexisting data needs to be removed
        if(SurveyActivity.isApplicationInEditMode){
            removeSurveyFromDatabase(survey.surveyId.toString())

        }
        addCompleteSurvey(survey, listOfSors, checklist)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    private suspend fun addCompleteSurvey(survey: Survey, listOfSors: List<SurveySORs>, checklist: ChecklistEntries) {
        dbManager.insertSurvey(survey)
        dbManager.insertSurveySorList(listOfSors)
        dbManager.insertCheckList(checklist)
    }



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun removeSurveyFromDatabase(sor: String) {
        dbManager.removeSurveyFromTable(sor)
        dbManager.removeCheckListFromTable(sor)
        dbManager.removeSurveySorsFromTable(sor)
    }




    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun returnSurveysByAddress(address:String):List<Survey>{
        val addressWithChar = "%"+address+"%"
        val list = dbManager.getSurveysByAddress(addressWithChar)
        return list
    }



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun returnSurveySors(sorId: Int): List<SurveySORs>?{
        var list:List<SurveySORs>?= null
        // Before entering an Sor code it checks if it exist
        val doesExist = dbManager.searchForSurveyByID(sorId)
        Log.i("SEARCH", "Does exist " + doesExist.surveyorName)
        if (doesExist != null) {
         list =   dbManager.getSurveySors(sorId)
        }
        return list
    }





    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getCheckList(surveyId : Int):ChecklistEntries {
        val  checkListObject = dbManager.getCheckList(surveyId)

        return checkListObject
    }




    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(sor: SoR) {
        // Before entering an Sor code it checks if it exist
        val doesExist = dbManager.getSoR(sor.sorCode)
        if (doesExist == null) {
            dbManager.insertSoR(sor)
        }
    }



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getSor(sor: String): SoR {
        val sor = dbManager.getSoR(sor)
        return sor
    }




    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getLastestSurvey(): Survey {
      return dbManager.getLastID()
    }






    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun searchSurveyById(id : Int): Survey {

      val survey =  dbManager.searchForSurveyByID(id)

        return survey
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
   suspend fun returnSurveysByMonth(month: Int): List<Survey> {
        val surveysByMonth = dbManager.getSurveyByMonth(month)

        return  surveysByMonth
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun returnSurveysByYear(year: Int): List<Survey>? {
        val surveysByYear = dbManager.getSurveyByYear(year)

        return surveysByYear
    }


}