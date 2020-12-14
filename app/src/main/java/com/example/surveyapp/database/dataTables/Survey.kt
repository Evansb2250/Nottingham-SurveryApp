package com.example.surveyapp.database.dataTables

import androidx.room.*
import com.example.surveyapp.database.dataTables.Survey.Companion.ADDRESS
import com.example.surveyapp.database.dataTables.Survey.Companion.POSTCODE
import com.example.surveyapp.database.dataTables.Survey.Companion.TABLE_NAME

//@Entity(tableName = TABLE_NAME, foreignKeys = [ForeignKey(entity = House::class,
//    parentColumns =["address", "postCode"],
//    childColumns = [ADDRESS,POSTCODE ]
//    ,onDelete = ForeignKey.CASCADE)] )
@Entity(tableName = TABLE_NAME)
data class Survey(
    @PrimaryKey(autoGenerate = true)
    //TODO add the column fields and foreign keys
    var surveyId: Long,
    @ColumnInfo(name = ADDRESS) var address: String,
    @ColumnInfo(name = POSTCODE) var postCode: String,
    @ColumnInfo(name = SURVEYOR) var surveyorName: String,
    @ColumnInfo(name = PHONENUMBER) var phoneNumber: String,
    // @ColumnInfo(name = ABESTOREMOVAL) var abestoRemovalDescription: String,
    //TODO look into creating a date variable
    // @ColumnInfo(name= DATE) var Date: String,
//    @ColumnInfo(name= SURVEYTYPE) var surveyType: String

    /*
    https://stackoverflow.com/questions/47511750/how-to-use-foreign-key-in-room-persistence-library
    @Entity(foreignKeys = arrayOf(ForeignKey(entity = ParentClass::class,
                    parentColumns = arrayOf("parentClassColumn"),
                    childColumns = arrayOf("childClassColumn"),
                    onDelete = ForeignKey.CASCADE)))
     */

) {
    companion object {
        const val TABLE_NAME = "Survey_table"
        const val ADDRESS = "address"
        const val POSTCODE = "postCode"
        const val SURVEYOR = "Surveyor"
        const val PHONENUMBER = "phoneNumber"
        const val ABESTOREMOVAL = "abestoRemoval"
        const val DATE = "date"
        const val SURVEYTYPE = "surveyType"
    }
}
