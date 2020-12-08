package com.example.surveyapp.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Survey_table")
data class Survey(
    @PrimaryKey(autoGenerate = true)
    var surveyId: Long,
    var address: String,
    var postCode: String,


    /*
    https://stackoverflow.com/questions/47511750/how-to-use-foreign-key-in-room-persistence-library
    @Entity(foreignKeys = arrayOf(ForeignKey(entity = ParentClass::class,
                    parentColumns = arrayOf("parentClassColumn"),
                    childColumns = arrayOf("childClassColumn"),
                    onDelete = ForeignKey.CASCADE)))
     */


)
