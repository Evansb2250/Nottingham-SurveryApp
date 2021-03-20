package com.example.surveyapp.domains

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checklist_table")
data class ChecklistEntries(
    @PrimaryKey var surveyId: Int,
    @ColumnInfo(name = HeatType) var heatType:Int,
    @ColumnInfo(name = FireDoor) var fireDoor: Boolean,
    @ColumnInfo(name = FireDoorComment) var fireDoorComment: String,
    @ColumnInfo(name = DecorationPoints) var decorationPoints: String,
    @ColumnInfo(name = LocationTaps) var locationTaps: String,
    @ColumnInfo(name = FloorLevel) var floorLevel: String,
    @ColumnInfo(name = Isolator) var isolator: Boolean,
    @ColumnInfo(name = MeterIssue) var meterIssue: Boolean,
    @ColumnInfo(name = FastTrack) var fastTrack: Boolean,
    @ColumnInfo(name = Altro) var altro: Boolean,
    @ColumnInfo(name = Rewire) var rewire: Boolean,
    @ColumnInfo(name = Heating) var heating: Boolean,
    @ColumnInfo(name = Glass)   var glass:Boolean


) {


    companion object {
        const val HeatType = "Heat_Type"
        const val FireDoor = "Fire_Door"
        const val FireDoorComment = "Fire_Door_Comment"
        const val DecorationPoints = "Decoration_Point"
        const val LocationTaps ="Location_Taps"
        const val FloorLevel = "Floor_Level"
        const val Isolator="Isolator"
        const val MeterIssue = "Meter_Issue"
        const val FastTrack = "Fast_Track"
        const val Altro = "Altro"
        const val Rewire = "Rewire"
        const val Heating = "Heating"
        const val Glass = "Glass"
        const val AbestoRemoval ="Abesto_Removal"

    }
}