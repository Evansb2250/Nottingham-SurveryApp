package com.example.surveyapp.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.surveyapp.domains.SoR
import com.example.surveyapp.ignore.Survey
import kotlinx.coroutines.flow.Flow


@Dao
interface dbDAO {

    @Insert
    suspend fun insertSoR(sorCode: SoR)

    @Insert
    suspend fun insertSurvey(survey: Survey)

    @Update
    suspend fun updateSoR(sor: SoR)


    @Query("SELECT * from SoR_table WHERE sorCode= :key")
    suspend fun getSoR(key: String): SoR

    @Query("DELETE FROM SoR_table where sorCode =:key")
    suspend fun removeSoR(key: String)


}