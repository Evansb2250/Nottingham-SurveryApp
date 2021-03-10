package com.example.surveyapp.CONSTANTS

import androidx.collection.arraySetOf
import com.example.surveyapp.domains.SoR

class constant {
    companion object {
        val SORCODE = "SOR CODE"
        val KEYWORD = "Keyword"
        val CAPTIAL = "Capital"
        val REVENUE = "Revenue"
        val searchBy = arrayListOf<String>(SORCODE, KEYWORD)

        val PB3010 = "PB3010"
        val PB2020 = "PB2020"
        val PB4030 = "PB4030"
        val FP0010 = "FP0010"
        val PB2050 = "PB2050"
        val PB2030 = "PB2030"

        val DHEATING = PB3010
        val COMBIBOILER = arrayListOf<String>(PB3010, PB2030)
        val BOILERPOINTCODES = arrayListOf<String>(PB3010, PB2020, PB4030, FP0010, PB2050)

        /*
1.Revenue
2.response
3.Capital
4.response
5.Fire / services referral
6.reach
7.Rechargeable Total
8.amount
 */

        val HEADERS3 = arrayListOf<String>(
            "Category",
            "SoRs",
            "Description",
            "",
            "UOM",
            "QTY",
            "Recharge",
            "Total Price",
            "Comments",
            "Rechargeable Total",
        ""
        )
        val HEADERS2 = arrayListOf<String>(
            "Revenue",
            "",
            "Capital",
            "",
            "",
            "",
            "",
            "",
            "",
            "Capital Void Total",
            ""
        )
        val HEADERS1 = arrayListOf<String>(
            "Address",
            "",
            "",
            "Surveyor: ",
            "",
            "",
            "",
            "",
            "",
            "Revenue Void Total",
            ""

        )
        val STANDARDCODE = "Standard Code"
        val QUESTION1_SOR = "DW0090"
        val QUESTION2_SOR = "EN4530"
        val QUESTION3_SOR = "EN6160"
        val QUESTION4_SOR = "EN1010"
        val QUESTION5_SOR = "JN2030"
        val QUESTION6_SOR = "JN2070"
        val QUESTION7_SOR = "JN2040"
        val QUESTION8_SOR = "GR0510"
        val QUESTION9_SOR = "GR0520"
        val QUESTION10_SOR = "EN0510"
        val QUESTION11_SOR = "EN0570"
        val QUESTION12_SOR = "EN0610"
        val QUESTION13_SOR = "EN0650"
        val QUESTION14_SOR = "EN0660"
        val QUESTION15_SOR = "EN0530"
        val QUESTION16_SOR = "EN0540"
        val QUESTION17_SOR = "EN0550"

        val ROOMCATEGORIES = arrayListOf<String>(
            "",
            "Outside front",
            "Outside rear",
            "Kitchen",
            "Utility",
            "Lounge",
            "Dining/Parlour",
            "Hall",
            "Stairs",
            "Landing",
            "Single wc",
            "Bathroom",
            "Bedroom 1",
            "Bedroom 2",
            "Bedroom 3",
            "Bedroom 4",
            "Bedroom 5",
            "Attic room",
            "Garage/outhouse",
            "Misc/other"
        )

        val FIRE_DOOR_BOX_ID = 0
        val ISOLATOR_BOX_ID = 1
        val METER_BOX_ID =2
        val FAST_TRACKING_BOX_ID = 3
        val ASTO_BOX_ID = 4
        val REWIRE_BOX_ID = 5
        val HEATING_BOX_ID = 6
        val GLASS_BOX_ID = 7



        val NOTALLOWEDTOENTER = arraySetOf<String>(
            QUESTION1_SOR,
            QUESTION5_SOR,
            QUESTION6_SOR,
            QUESTION6_SOR,
            QUESTION7_SOR,
            QUESTION8_SOR,
            QUESTION9_SOR,
            QUESTION10_SOR,
            QUESTION11_SOR,
            QUESTION12_SOR,
            QUESTION13_SOR,
            QUESTION14_SOR,
            PB3010,
            PB2020,
            PB4030,
            FP0010,
            PB2050,
            PB2030
        )

        val quantityRange = arrayListOf<Int>(
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13,
            14,
            15,
            16,
            17,
            18,
            19,
            20
        )


        val bundleMessage = "message"
        val editSurveyTag = "edit"
        val idTag = "ID"
        val getLastSurvey = "requesting last survey"
        val getExistingSurvey ="requesting existing survey"


    }
}