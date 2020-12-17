package com.example.surveyapp.database.dataTables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SoRDatabaseDao {
    @Insert
    fun insert(sor: SoR)

    @Insert
    fun update(sor: SoR)

    @Query("SELECT * from sor_table WHERE sorCode= sorCode= :key")
    fun get(key: String): SoR
}