package com.example.surveyapp.fragments.checkListTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.Spinner
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.databinding.FragmentCreateSurveyP1p5Binding


class CreateSurveyP1p5Fragment : Fragment() {

    lateinit var binding: FragmentCreateSurveyP1p5Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_survey_p1p5,
            container,
            false
        )
        binding.viewmodel = SurveyActivity.checkListVM


        heatypeChange(binding.systemBoilerSpinner)
        // simplifying the checkBox click
        registerCheckBoxClick(1, binding.fdChkbx)
        registerCheckBoxClick(2, binding.isolatorChkBx)
        registerCheckBoxClick(3, binding.mIChkBx)
        registerCheckBoxClick(4, binding.ftChkBx)
        registerCheckBoxClick(5, binding.atroChkBx)
        registerCheckBoxClick(6, binding.rwChkBx)
        registerCheckBoxClick(7, binding.heatingChkBx)
        registerCheckBoxClick(8, binding.glassChkBx)


        binding.fireDoorTextField.addTextChangedListener { text ->
            SurveyActivity.checkListVM?.storeFireDoorComment(text.toString())

        }


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun registerCheckBoxClick(id: Int, chkBx: CheckBox) {

        chkBx.setOnClickListener { it ->
            val isChecked = chkBx.isChecked
            SurveyActivity.checkListVM?.registerClick(id, isChecked)
        }


    }

    private fun heatypeChange(heatingSpinner: Spinner) {
        heatingSpinner.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                SurveyActivity.checkListVM?.setHeatyingType(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


    }

}