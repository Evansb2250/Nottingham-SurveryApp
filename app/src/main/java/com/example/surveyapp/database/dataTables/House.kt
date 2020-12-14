package com.example.surveyapp.database.dataTables

import androidx.room.Entity

@Entity(
    tableName = "House_table",
    primaryKeys = arrayOf("address", "postCode"),
)
data class House(
    //TODO add Primary key to address and post code
    var address: String,
    var postCode: String,
    var floorLevel: Int,
    var hasFireDoor: Boolean,
    var hasHeating: Boolean,
    var heatingComment: String,
    var hasIsolator: Boolean,
    var heatingType: String,
    var isReWire: Boolean,
    var needsFastTrack: Boolean,
    var needsGlass: Boolean,
    var hasAltro: Boolean

)
