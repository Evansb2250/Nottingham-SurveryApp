package com.example.surveyapp.database.dataTables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

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