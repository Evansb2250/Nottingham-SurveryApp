package com.example.surveyapp.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.surveyapp.domains.SoR
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.ignore.Survey
import kotlinx.coroutines.flow.Flow


@Dao
interface dbDAO {

    @Insert
    suspend fun insertSoR(sorCode: SoR)

    @Insert
    suspend fun insertSurvey(survey: Survey)

    @Insert
    suspend fun insertSurveySors(sor: SurveySORs)

    @Update
    suspend fun updateSoR(sor: SoR)



    @Query("SELECT * from SoR_table WHERE sorCode= :key")
    suspend fun getSoR(key: String): SoR


    @Query("DELETE FROM SoR_table where sorCode =:key")
    suspend fun removeSoR(key: String)


    //Test function
    @Query("SELECT * from survey_table where surveyId =:key")
    suspend fun getKeySurveyTable(key:Int):Survey

    @Query("SELECT * from survey_table ORDER BY surveyId DESC LIMIT 0, 1")
    suspend fun getLastID():Survey



}