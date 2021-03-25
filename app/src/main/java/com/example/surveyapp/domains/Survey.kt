package com.example.surveyapp.ignore

import androidx.room.*
import com.example.surveyapp.ignore.Survey.Companion.TABLE_NAME


@Entity(tableName = TABLE_NAME)
data class Survey(
    @PrimaryKey(autoGenerate = true)
    //TODO add the column fields and foreign keys
    var surveyId: Int = 0,
    @ColumnInfo(name = ADDRESS) val address: String,
    @ColumnInfo(name = POSTCODE) val postCode: String,
    @ColumnInfo(name = SURVEYOR) val surveyorName: String,
    @ColumnInfo(name = PHONENUMBER) val phoneNumber: String,
    @ColumnInfo(name = ABESTOREMOVAL) val abestoRemovalDescription: String,
    //TODO look into creating a date variable
    @ColumnInfo(name = DAY) val day: Int, //TODO CREATE DAY, MONTH, YEAR columns
    @ColumnInfo(name= MONTH) val  month: Int,
    @ColumnInfo(name = YEAR) val year: Int,
    @ColumnInfo(name = SURVEYTYPE) val surveyType: String,
    @ColumnInfo(name = SURVEY_TOTAL) var surveyTotal:Double,
    @ColumnInfo(name=  SURVEY_RECHARGE_TOTAL) var rechargeTotal:Double,
    @ColumnInfo(name=  VAT_AMOUNT) var vatAmount:Double = .20

)

/*z
https://stackoverflow.com/questions/47511750/how-to-use-foreign-key-in-room-persistence-library
@Entity(foreignKeys = arrayOf(ForeignKey(entity = ParentClass::class,
                parentColumns = arrayOf("parentClassColumn"),
                childColumns = arrayOf("childClassColumn"),
                onDelete = ForeignKey.CASCADE)))
 */ {
    companion object {
        const val TABLE_NAME = "Survey_table"
        const val ADDRESS = "address"
        const val POSTCODE = "postCode"
        const val SURVEYOR = "Surveyor"
        const val PHONENUMBER = "phoneNumber"
        const val ABESTOREMOVAL = "abestoRemoval"
        const val DAY = "day"
        const val MONTH = "month"
        const val YEAR = "year"
        const val SURVEYTYPE = "surveyType"
        const val SURVEY_TOTAL ="survey_Total"
        const val SURVEY_RECHARGE_TOTAL ="survey_recharge_Total"
        const val VAT_AMOUNT = "vat_amount"

    }
}
