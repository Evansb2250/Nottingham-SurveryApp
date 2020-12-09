package com.example.surveyapp.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Survey_table")
data class Survey(
    @PrimaryKey(autoGenerate = true)
    //TODO add the column fields and foreign keys
    var surveyId: Long,
    var address: String,
    var postCode: String,
    var surveyorName: String,
    var phoneNumber: String,
    var abestoRemovalDescription: String,
    //TODO look into creating a date variable
    var Date: String


    /*
    https://stackoverflow.com/questions/47511750/how-to-use-foreign-key-in-room-persistence-library
    @Entity(foreignKeys = arrayOf(ForeignKey(entity = ParentClass::class,
                    parentColumns = arrayOf("parentClassColumn"),
                    childColumns = arrayOf("childClassColumn"),
                    onDelete = ForeignKey.CASCADE)))
     */


)
