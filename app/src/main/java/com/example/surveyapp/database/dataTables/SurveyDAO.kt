package com.example.surveyapp.database.dataTables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SurveyDAO {

    @Query("SELECT * FROM survey_table ORDER BY surveyId ASC")
    fun getAlphabetizedWords(): List<Survey>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(survey: Survey)

    @Query("DELETE FROM Survey_table")
    suspend fun deleteAll()
}