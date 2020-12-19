package com.example.surveyapp.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.surveyapp.domains.SoR
import kotlinx.coroutines.flow.Flow


@Dao
interface dbDAO {

    @Insert
    suspend fun insertSoR(sorCode: SoR)

    @Update
    suspend fun updateSoR(sor: SoR)


    @Query("SELECT * from SoR_table WHERE sorCode= :key")
    suspend fun getSoR(key: String): SoR


}