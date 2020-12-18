package com.example.surveyapp.database.dataTables

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.surveyapp.DAO.SoRDatabaseDao
import com.example.surveyapp.DAO.SurveyDAO

@Database(entities = [SoR::class, Survey::class], version = 1, exportSchema = false)
abstract class SurveyDatabase : RoomDatabase() {

    //make the SurveyDatabse know about the Dao
    abstract val sordatabaseDao: SoRDatabaseDao
    abstract val surveydao: SurveyDAO

    //    abstract val surveydatabaseDao : SurveyDAO
//
    companion object {
        @Volatile
        private var INSTANCE: SurveyDatabase? = null

        fun getInstance(context: Context): SurveyDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SurveyDatabase::class.java, "Suvery_History_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }
        }

    }

}
