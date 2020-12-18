package com.example.surveyapp.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.surveyapp.database.dataTables.SoR

@Dao
interface SoRDatabaseDao {
    @Insert
    fun insert(sor: SoR)

    @Update
    fun update(sor: SoR)

    @Query("SELECT * from SoR_table WHERE sorCode= :key")
    fun get(key: String): SoR


//    override fun getAlphabetizedWords(): List<Survey> {
//        TODO("Not yet implemented")
//    }
}