package com.example.surveyapp.domains//package com.example.surveyapp.database.dataTables


data class SurveySORs(
    var sorCode: String,
    var surveyID: Int,
    var surveyorDescription: String,
    var isRecharge: Boolean,
    var quantity: Double,
    var total: Double
)
