package com.example.surveyapp.fragments.checkListTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.surveyapp.CONSTANTS.constant
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
        binding.lifecycleOwner = this


        binding.decorComments.addTextChangedListener{
            val decorText = binding.decorComments.getText().toString()
            SurveyActivity.checkListVM?.updateDecorTaps(decorText)
        }

        binding.floorComment.addTextChangedListener{
            val floorText = binding.floorComment.getText().toString()
            SurveyActivity.checkListVM?.upDateFloorComment(floorText)
        }

        binding.TAPScomment.addTextChangedListener{
            val tapsText = binding.TAPScomment.getText().toString()
            SurveyActivity.checkListVM?.upDateTapsComment(tapsText)
        }




        heatypeChange(binding.systemBoilerSpinner)
        // simplifying the checkBox click
        registerCheckBoxClick(constant.FIRE_DOOR_BOX_ID, binding.fdChkbx)
        registerCheckBoxClick(constant.ISOLATOR_BOX_ID, binding.isolatorChkBx)
        registerCheckBoxClick(constant.METER_BOX_ID, binding.mIChkBx)
        registerCheckBoxClick(constant.FAST_TRACKING_BOX_ID, binding.ftChkBx)
        registerCheckBoxClick(constant.ASTO_BOX_ID, binding.atroChkBx)
        registerCheckBoxClick(constant.REWIRE_BOX_ID, binding.rwChkBx)
        registerCheckBoxClick(constant.HEATING_BOX_ID, binding.heatingChkBx)
        registerCheckBoxClick(constant.GLASS_BOX_ID, binding.glassChkBx)

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

            Toast.makeText(requireContext(), binding.viewmodel!!.getDecorPoints(), Toast.LENGTH_LONG).show()
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