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

        registerTextChange(1, binding.question1)
        registerTextChange(5, binding.question5)
        registerTextChange(6, binding.question6)
        registerTextChange(7, binding.question7)
        registerTextChange(8, binding.question8)
        registerTextChange(9, binding.question9)
        registerTextChange(10, binding.question10)
        registerTextChange(11, binding.question11)
        registerTextChange(12, binding.question12)
        registerTextChange(13, binding.question13)
        registerTextChange(14, binding.question14)
        registerTextChange(15, binding.question15)
        registerTextChange(16, binding.question16)




        registerRechargeClick(1, binding.rechargeQ1)
        registerRechargeClick(2, binding.rechargeQ2)
        registerRechargeClick(3, binding.rechargeQ3)
        registerRechargeClick(4, binding.rechargeQ4)
        registerRechargeClick(5, binding.rechargeQ5)
        registerRechargeClick(6, binding.rechargeQ6)
        registerRechargeClick(7, binding.rechargeQ7)
        registerRechargeClick(8, binding.rechargeQ8)
        registerRechargeClick(9, binding.rechargeQ9)
        registerRechargeClick(10, binding.rechargeQ10)
        registerRechargeClick(11, binding.rechargeQ11)
        registerRechargeClick(12, binding.rechargeQ12)
        registerRechargeClick(13, binding.rechargeQ13)
        registerRechargeClick(14, binding.rechargeQ14)
        registerRechargeClick(15, binding.rechargeQ15)
        registerRechargeClick(16, binding.rechargeQ16)


        return binding.root
    }


    private fun registerTextChange(id: Int, questionEntry: EditText) {
        questionEntry.addTextChangedListener {
            val number = questionEntry.text
            Toast.makeText(requireContext(), "Slot " + id + " has " + number, Toast.LENGTH_SHORT)
                .show()
        }
    }


    private fun registerRechargeClick(id: Int, rechargeBox: CheckBox) {

        rechargeBox.setOnClickListener {
            Toast.makeText(requireContext(), "was clicked" + id, Toast.LENGTH_SHORT).show()
        }
    }


}