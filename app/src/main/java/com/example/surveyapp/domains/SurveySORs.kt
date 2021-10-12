package com.example.surveyapp.domains

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//package com.example.surveyapp.database.dataTables



@Entity(tableName = "survey_sors_table", primaryKeys= arrayOf("uniqueSurveyCode", "sorCode", "surveyID"))
data class SurveySORs(
    var uniqueSurveyCode: Int,
    var surveyID: Int,
    var sorCode: String = "",
    @ColumnInfo(name="UOM") var UOM: String,
    @ColumnInfo(name="Sor_Description") var sorDescription: String,
    @ColumnInfo(name="Survey_Description") var surveyorDescription: String,
    @ColumnInfo(name="isRecharge") var isRecharge: Boolean,
    @ColumnInfo(name="Quantity") var quantity: Double,
    @ColumnInfo(name="Total") var total: Double, //Change to recharge rate
    @ColumnInfo(name="Category") var roomCategory: String = "",

)
