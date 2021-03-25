package com.example.surveyapp.domains

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "SoR_table")
data class SoR(
    @PrimaryKey var sorCode: String,
    @ColumnInfo(name = "Description") var description: String,
    @ColumnInfo(name = "UOM") var UOM: String,
    @ColumnInfo(name = "Recharge_Price") var rechargeRate: Double
)
