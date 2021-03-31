package com.example.surveyapp.fragments.checkListTab

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.Spinner
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.databinding.FragmentCreateSurveyP1p5Binding
import com.example.surveyapp.ignore.Survey


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

        SurveyActivity.checkListVM?.changeDetected?.observe(viewLifecycleOwner, Observer { change ->
              loadCheckBoxState()

              loadCommentsToGui()
              binding.systemBoilerSpinner.setSelection(SurveyActivity.checkListVM!!.getHeatTypeIndex())
        })

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

    private fun loadCommentsToGui() {
        binding.floorComment.setText(SurveyActivity.checkListVM?.getFloorLevel())
        binding.decorComments.setText(SurveyActivity.checkListVM?.getDecorPoints())
        binding.fireDoorTextField.setText(SurveyActivity.checkListVM?.getFireDoorComments())
        binding.TAPScomment.setText(SurveyActivity.checkListVM?.getTapsLocation())
    }

    private fun loadCheckBoxState() {
        binding.fdChkbx.setChecked(SurveyActivity.checkListVM!!.checBoxLiveState_[constant.FIRE_DOOR_BOX_ID])

        binding.fdChkbx.jumpDrawablesToCurrentState()

        binding.isolatorChkBx.setChecked(SurveyActivity.checkListVM!!.checBoxLiveState_[constant.ISOLATOR_BOX_ID])
        binding.isolatorChkBx.jumpDrawablesToCurrentState()

        binding.mIChkBx.setChecked(SurveyActivity.checkListVM!!.checBoxLiveState_[constant.METER_BOX_ID])
        binding.mIChkBx.jumpDrawablesToCurrentState()

        binding.ftChkBx.setChecked(SurveyActivity.checkListVM!!.checBoxLiveState_[constant.FAST_TRACKING_BOX_ID])
        binding.ftChkBx.jumpDrawablesToCurrentState()

        binding.atroChkBx.setChecked(SurveyActivity.checkListVM!!.checBoxLiveState_[constant.ASTO_BOX_ID])
        binding.atroChkBx.jumpDrawablesToCurrentState()

        binding.rwChkBx.setChecked(SurveyActivity.checkListVM!!.checBoxLiveState_[constant.REWIRE_BOX_ID])
        binding.rwChkBx.jumpDrawablesToCurrentState()

        binding.heatingChkBx.setChecked(SurveyActivity.checkListVM!!.checBoxLiveState_[constant.HEATING_BOX_ID])
        binding.heatingChkBx.jumpDrawablesToCurrentState()

        binding.glassChkBx.setChecked(SurveyActivity.checkListVM!!.checBoxLiveState_[constant.GLASS_BOX_ID])
        binding.glassChkBx.jumpDrawablesToCurrentState()
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

            }

        }


    }

}