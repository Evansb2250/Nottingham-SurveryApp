package com.example.surveyapp.repository

import androidx.annotation.WorkerThread
import com.example.surveyapp.DAO.dbDAO
import com.example.surveyapp.domains.SoR

class DatabaseRepository(private val dbManager: dbDAO) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(sor: SoR) {
        // Before entering an Sor code it checks if it exist
        if (dbManager.getSoR(sor.sorCode) == null) {
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
    suspend fun removeFromSoR(sor: String) {
        dbManager.removeSoR(sor)
    }


}