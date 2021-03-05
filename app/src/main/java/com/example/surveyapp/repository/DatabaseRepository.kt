package com.example.surveyapp.repository

import androidx.annotation.WorkerThread
import com.example.surveyapp.DAO.dbDAO
import com.example.surveyapp.domains.SoR
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.ignore.Survey

class DatabaseRepository(private val dbManager: dbDAO) {

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
    suspend fun insertCompleteSurvey(survey: Survey) {
        dbManager.insertSurvey(survey)

    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getSor(sor: String): SoR {
        val sor = dbManager.getSoR(sor)
        return sor
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun removeFromSoR(sor: String) {
        dbManager.removeSoR(sor)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getLastestSurvey(): Survey {
      return dbManager.getLastID()
    }



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertSurveySors(sor: SurveySORs) {

                dbManager.insertSurveySors(sor)

    }



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun searchSurveyById(id : Int): Survey {

      val survey =  dbManager.searchForSurveyByID(id)

        return survey
    }




}