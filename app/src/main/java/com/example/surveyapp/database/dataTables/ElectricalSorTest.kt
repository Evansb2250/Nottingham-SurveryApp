package com.example.surveyapp.database.dataTables

data class ElectricalSorTest(
    val etsID: Int,
    //actual description ( Periodic inspection in void properties)
    var numOfVoidProperties: Int,
    // Installation certification - in conjunction
    var numOfCertInstall: Int,
    //Smoke alarm certificates
    var numOfSmokeAlarmCerts: Int,
    //Check, test, and fault find electrics before repairs
    var numOfElectricFaultsBeforeTest: Int,
    //Check, test, and fault find electrics after repairs
    val numOfElectricFaultsAfterTest: Int,
    //Additional radial circuit
    var numOfRadialCircuits: Int,
    //additional lighting circuit
    var numOfLightingCircuits: Int,
    //Additional ring main circuit
    var numOfRingMainCircuits: Int
)
