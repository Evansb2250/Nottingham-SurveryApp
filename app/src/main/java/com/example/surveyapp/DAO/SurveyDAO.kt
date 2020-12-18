package com.example.surveyapp.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.surveyapp.database.dataTables.Survey

@Dao
interface SurveyDAO {

//    @Query("SELECT * FROM survey_table ORDER BY surveyId ASC")
//    fun getAlphabetizedWords(): List<Survey>


    @Insert
    fun insertSurvey(survey: Survey)

    @Query("SELECT * FROM survey_table WHERE surveyId = :surveyId")
    fun get(surveyId: Int): Survey

//    @Update
//    fun update()

//    @Query("DELETE FROM Survey_table")
//    fun deleteAll()
}