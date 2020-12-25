package com.example.surveyapp.fragments.PreviousWorkAndLockTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.databinding.FragmentCreateSurveyP2Binding


class CreateSurveyP2Fragment : Fragment() {


    private lateinit var binding: FragmentCreateSurveyP2Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_survey_p2, container, false)

        registerTextChange(1, binding.question1, binding.rechargeQ1)
        registerTextChange(5, binding.question5, binding.rechargeQ5)
        registerTextChange(6, binding.question6, binding.rechargeQ6)
        registerTextChange(7, binding.question7, binding.rechargeQ7)
        registerTextChange(8, binding.question8, binding.rechargeQ8)
        registerTextChange(9, binding.question9, binding.rechargeQ9)
        registerTextChange(10, binding.question10, binding.rechargeQ10)
        registerTextChange(11, binding.question11, binding.rechargeQ11)
        registerTextChange(12, binding.question12, binding.rechargeQ12)
        registerTextChange(13, binding.question13, binding.rechargeQ13)
        registerTextChange(14, binding.question14, binding.rechargeQ14)
        registerTextChange(15, binding.question15, binding.rechargeQ15)
        registerTextChange(16, binding.question16, binding.rechargeQ16)




        registerRechargeClick(1, binding.rechargeQ1, binding.question1)
//        registerRechargeClick(2, binding.rechargeQ2)
//        registerRechargeClick(3, binding.rechargeQ3)
//        registerRechargeClick(4, binding.rechargeQ4)
        registerRechargeClick(5, binding.rechargeQ5, binding.question5)
        registerRechargeClick(6, binding.rechargeQ6, binding.question6)
        registerRechargeClick(7, binding.rechargeQ7, binding.question7)
        registerRechargeClick(8, binding.rechargeQ8, binding.question8)
        registerRechargeClick(9, binding.rechargeQ9, binding.question9)
        registerRechargeClick(10, binding.rechargeQ10, binding.question10)
        registerRechargeClick(11, binding.rechargeQ11, binding.question11)
        registerRechargeClick(12, binding.rechargeQ12, binding.question12)
        registerRechargeClick(13, binding.rechargeQ13, binding.question13)
        registerRechargeClick(14, binding.rechargeQ14, binding.question14)
        registerRechargeClick(15, binding.rechargeQ15, binding.question15)
        registerRechargeClick(16, binding.rechargeQ16, binding.question16)


        return binding.root
    }


    private fun registerTextChange(id: Int, questionEntry: EditText, rechargeBox: CheckBox) {
        questionEntry.addTextChangedListener {
            var Testnumber = questionEntry.text.toString().trim()
            val isNumberSuitable = testNumberLength(Testnumber)
            //pass the number Test

            if (isNumberSuitable) {

                var number: Int = returnAnyNumberWithoutError(Testnumber)

                val isrecharge = rechargeBox.isChecked


                SurveyActivity.prevViewModel!!.addChangesToVariable(id, number, isrecharge)
            } else
                questionEntry.setText("")
//            Toast.makeText(requireContext(), "Slot " + id + " has " + number, Toast.LENGTH_SHORT)
//                .show()
        }
    }

    private fun testNumberLength(testnumber: String): Boolean {


        if (testnumber.length > 4) {
            Toast.makeText(requireContext(), "NUMBER TOO LARGE!!", Toast.LENGTH_SHORT).show()
            return false
        } else
            return true
    }


    fun returnAnyNumberWithoutError(numberToCheck: String): Int {
        var testNumber = numberToCheck

        if (testNumber.equals("")) {
            testNumber = "0"
            return testNumber.toInt()
        } else
            return testNumber.toInt()

    }


    private fun registerRechargeClick(id: Int, rechargeBox: CheckBox, questionEntry: EditText) {

        rechargeBox.setOnClickListener {
            Toast.makeText(requireContext(), "was clicked" + id, Toast.LENGTH_SHORT).show()
        }
    }


}