package com.example.surveyapp.database.dataTables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "SoR_table")
data class SoR(
    @PrimaryKey val sorCode: String,
    @ColumnInfo(name = "Description") val description: String,
    @ColumnInfo(name = "Recharge_Price") val rechargeRate: Double
)
