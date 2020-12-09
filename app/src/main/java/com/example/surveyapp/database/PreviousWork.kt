package com.example.surveyapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PreviosWork_table")
data class PreviousWork(
    @PrimaryKey(autoGenerate = true) var previousWorID: Int,
    // TODO add foreign key (Survey_ID),
    var labourMinutes: Int,
    var isElectricCookerDisconnected: Boolean,
    var iscookerDecom: Boolean,
    var isSupplyFitToMain: Boolean,


    )
