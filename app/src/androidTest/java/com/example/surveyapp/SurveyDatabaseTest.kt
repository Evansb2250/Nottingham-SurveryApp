package com.example.surveyapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.surveyapp.database.dataTables.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class SurveyDatabaseTest {

    private lateinit var SoRDatabaseDao: SoRDatabaseDao

    //  private lateinit var surveyDao : SurveyDAO
    private lateinit var db: SurveyDatabase

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.example.surveyapp", appContext.packageName)
    }

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SurveyDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        SoRDatabaseDao = db.sordatabaseDao
//        surveyDao = db.sordatabaseDao

    }


//    @Test
//    fun insertSurvey(){
//        val survey = Survey(1, "1103 Medford", "NG11 8HH",
//            "Samuel Brandenburg","+44-743-3211", "THe ssseedcxsada" ,
//                    "1991/02/01", "Capital")
//
//        surveyDao.insert(survey)
//
//        val s = surveyDao.get(1)
//
//        Assert.assertEquals(s.surveyId, 1)
//
//
//    }


//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }


    @Test
    @Throws(Exception::class)
    fun insert() {
        val sor = SoR("BK0010", "	Excavate in soil/clay not exceeding 1.5m	", 92.79)
        SoRDatabaseDao.insert(sor)
        // val tonight = SoRDatabaseDao.get
        val sorCode = SoRDatabaseDao.get("BK0010")
        Assert.assertEquals(sorCode.sorCode.toString().trim(), "BK0010")
    }


}