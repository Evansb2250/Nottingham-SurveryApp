package com.example.surveyapp.classLoader

import android.util.Log
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.domains.SurveySORs
import com.example.surveyapp.ignore.Survey

class RestoreSurveyHelper(private val surveyOld: Survey, private val sorsOld: List<SurveySORs>) {

    private var survey = surveyOld
    private var existingSors = sorsOld
    private var heatType: Int = 0
    private lateinit var sorsInSorFragment: List<SurveySORs>


    init {
        sorsInSorFragment = separateSorFramgent()
        //    sorsApartOfPreviousWork=  separtePreviousSors()
    }


    fun getSorsBelongingToSorFragament(): List<SurveySORs> {
        return sorsInSorFragment
    }


    fun getHeatType(): Int {
        heatType = 0

        return heatType
    }


    private fun separateSorFramgent(): List<SurveySORs> {
        var list = arrayListOf<SurveySORs>()

        for (x in 0..sorsOld.size - 1) {
            var answer = constant.NOTALLOWEDTOENTER.find { it == sorsOld[x].sorCode }
            if (answer == null) {
                list.add(sorsOld[x])
            }
        }

        return list
    }


    fun getAsbesto(): String {

        return survey.abestoRemovalDescription
    }


    fun getId(): Int? {
        return survey.surveyId
    }

    fun getName(): String {
        return survey.surveyorName
    }

    fun returnCreatePagePackage(): createPagePackage {

       val cPackage = createPagePackage(
            survey.surveyorName,
            survey.address,
            survey.postCode,
            survey.phoneNumber,
            survey.day,
            survey.month,
            survey.year,
            survey.surveyType
        )
        return cPackage
    }


}

class createPagePackage(
    surveyName: String, address: String, postCode: String, phoneNumber: String,
    day: Int, month: Int, year: Int, SurveyType: String
) {
    val name = surveyName
    val address = address
    val postCode = postCode
    val phoneNumber = phoneNumber
    val day = day
    val month = month
    val year = year
    val surveyType = SurveyType
}