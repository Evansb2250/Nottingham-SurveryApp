//package com.example.surveyapp.ignore
//
//import androidx.room.*
//
////@Entity(tableName = TABLE_NAME, foreignKeys = [ForeignKey(entity = House::class,
////    parentColumns =["address", "postCode"],
////    childColumns = [ADDRESS,POSTCODE ]
////    ,onDelete = ForeignKey.CASCADE)] )
//@Entity(tableName = "survey_table")
//data class Survey(
//    @PrimaryKey(autoGenerate = true)
//    //TODO add the column fields and foreign keys
//    val surveyId: Long,
//    @ColumnInfo(name = ADDRESS) val address: String,
//    @ColumnInfo(name = POSTCODE) val postCode: String,
//    @ColumnInfo(name = SURVEYOR) val surveyorName: String,
//    @ColumnInfo(name = PHONENUMBER) val phoneNumber: String,
//    @ColumnInfo(name = ABESTOREMOVAL) val abestoRemovalDescription: String,
//    //TODO look into creating a date variable
//    @ColumnInfo(name = DATE) val Date: String,
//    @ColumnInfo(name = SURVEYTYPE) val surveyType: String
//)
//
///*z
//https://stackoverflow.com/questions/47511750/how-to-use-foreign-key-in-room-persistence-library
//@Entity(foreignKeys = arrayOf(ForeignKey(entity = ParentClass::class,
//                parentColumns = arrayOf("parentClassColumn"),
//                childColumns = arrayOf("childClassColumn"),
//                onDelete = ForeignKey.CASCADE)))
// */ {
//    companion object {
//        const val TABLE_NAME = "Survey_table"
//        const val ADDRESS = "address"
//        const val POSTCODE = "postCode"
//        const val SURVEYOR = "Surveyor"
//        const val PHONENUMBER = "phoneNumber"
//        const val ABESTOREMOVAL = "abestoRemoval"
//        const val DATE = "date"
//        const val SURVEYTYPE = "surveyType"
//    }
//}
