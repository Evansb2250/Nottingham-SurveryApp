package com.example.surveyapp.database.dataTables

data class Locks(
    var lockID: Int,
    var numOfInsuranceFit: Int,
    var numOfInsuranceLockFit: Int,
    var numOfCylinderLockRenew: Int,
    var numOfFixOnlyLock: Int,
    var numOfDoubleGlazed50: Int,
    var numOfDouleGlazed1p9: Int,
)
