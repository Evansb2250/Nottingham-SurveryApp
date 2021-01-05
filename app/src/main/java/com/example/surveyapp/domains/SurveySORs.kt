package com.example.surveyapp.domains//package com.example.surveyapp.database.dataTables


data class SurveySORs(
    var sorCode: String,
    var UOM: String,
    var surveyID: Int,
    var sorDescription: String,
    var surveyorDescription: String,
    var isRecharge: Boolean,
    var quantity: Double,
    var total: Double,
    var roomCategory: String = ""
)
