package com.example.surveyapp.application

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.example.surveyapp.database.SurveyDatabase
import com.example.surveyapp.repository.DatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SurveyApplication : MultiDexApplication() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())


    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts

    val database by lazy { SurveyDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { DatabaseRepository(database.dbManager()) }

}