package com.example.surveyapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PreviosWork_table")
data class PreviousWork(
    @PrimaryKey val address: String,
    @PrimaryKey val postCode: String
)
