package com.example.surveyapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.surveyapp.CONSTANTS.ExistingSors
import com.example.surveyapp.DAO.dbDAO
import com.example.surveyapp.domains.SoR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [SoR::class], version = 1, exportSchema = false)
abstract class SurveyDatabase : RoomDatabase() {

    //let the database know about the DAO
    abstract fun dbManager(): dbDAO

    private class SoRDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.dbManager())
                }
            }
        }

        suspend fun populateDatabase(dbManager: dbDAO) {
            // Delete all content here.
            val sorList = ExistingSors.getList()
            // TODO: Add your own words!
            for (sorCode in sorList) {
                dbManager.insertSoR(sorCode)
            }
        }
    }


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        // create an instance of the database inside the companion object
        //reduce the risk of multiple instances being created
        @Volatile
        private var INSTANCE: SurveyDatabase? = null


        fun getDatabase(context: Context, scope: CoroutineScope): SurveyDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SurveyDatabase::class.java,
                    "Survey_Database"
                ).addCallback(SoRDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }


    }


}

