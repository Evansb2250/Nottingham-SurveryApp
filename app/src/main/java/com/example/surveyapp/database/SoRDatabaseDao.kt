package com.example.surveyapp.database

import androidx.room.Insert
import androidx.room.Query

interface SoRDatabaseDao {
    @Insert
    fun insert(sor: SoR)

    @Insert
    fun update(sor: SoR)

    @Query("SELECT * from sor_table WHERE sorCode= sorCode= :key")
    fun get(key: String): SoR
}