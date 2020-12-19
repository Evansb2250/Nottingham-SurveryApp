package com.example.surveyapp.repository

import androidx.annotation.WorkerThread
import com.example.surveyapp.DAO.dbDAO
import com.example.surveyapp.domains.SoR

class DatabaseRepository(private val dbManager: dbDAO) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(sor: SoR) {
        dbManager.insertSoR(sor)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getSor(sor: String) {
        dbManager.getSoR(sor)
    }


//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun removeFromSoR(sor : String){
//        dbManager.removeSoR(sor)
//    }


}