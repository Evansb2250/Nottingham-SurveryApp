package com.example.surveyapp.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.surveyapp.domains.ChecklistEntries
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
    suspend fun insertSurveySorList(sorList: List<SurveySORs>)

    @Insert
    suspend fun  insertCheckList(checklist: ChecklistEntries)

    @Update
    suspend fun updateSoR(sor: SoR)



    //Used to get more data about the
    @Query("SELECT * from SoR_table WHERE sorCode=:key")
    suspend fun getSoR(key: String): SoR


    @Query("SELECT * FROM SURVEY_TABLE where surveyId =:key")
    suspend fun searchForSurveyByID(key:Int):Survey

    @Query("SELECT * FROM survey_sors_table where surveyId =:key")
    suspend fun getSurveySors(key:Int): List<SurveySORs>





    //USED TO OFFSET SURVEY ID BY 1 to avoid duplicate key ERROR
    @Query("SELECT * from survey_table ORDER BY surveyId DESC LIMIT 0, 1")
    suspend fun getLastID():Survey





    //Functions for returning a complete survey
    @Query("SELECT * from survey_table where surveyId =:key")
    suspend fun getKeySurveyTable(key:Int):Survey

    @Query("Select * from checklist_table Where surveyId=:key")
    suspend fun getCheckList(key: Int):ChecklistEntries

    @Query("Select * from survey_table where address Like :address ")
    suspend fun getSurveysByAddress(address:String):List<Survey>

    @Query("Select * from survey_table where month Like :month ")
    suspend fun getSurveyByMonth(month: Int): List<Survey>

    @Query("Select * from survey_table where year Like :year ")
    suspend fun getSurveyByYear(year: Int): List<Survey>?





    /*
    FUNCTIONS FOR REMOVING DATA FROM TABLE
     */
    @Query("DELETE FROM Survey_table where surveyId =:key ")
    suspend fun removeSurveyFromTable(key: String)

    @Query("DELETE FROM survey_sors_table where surveyID =:key ")
    suspend fun removeSurveySorsFromTable(key: String)

    @Query("DELETE FROM checklist_table where surveyId=:key ")
    suspend fun removeCheckListFromTable(key: String)

}