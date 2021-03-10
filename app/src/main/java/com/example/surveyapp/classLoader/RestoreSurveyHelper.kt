package com.example.surveyapp.classLoader

import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.ignore.Survey

class RestoreSurveyHelper(private val surveyOld: Survey, private val sorsOld: List<SurveySORs>) {


    var survey = surveyOld
    var existingSors =sorsOld


    fun getId(): Int? {
        return survey.surveyId
    }

    fun getName(): String {
        return survey.surveyorName
    }

    fun returnCreatePagePackage():createPagePackage{
        val cPackage = createPagePackage(survey.surveyorName, survey.address, survey.postCode, survey.phoneNumber,survey.Date, survey.surveyType )
        return cPackage
    }




}

class createPagePackage(surveyName: String, address:String, postCode:String, phoneNumber:String,
                      date:String, SurveyType:String){
    val name = surveyName
    val address = address
    val postCode = postCode
    val phoneNumber = phoneNumber
    val date = date
    val surveyType = SurveyType
}