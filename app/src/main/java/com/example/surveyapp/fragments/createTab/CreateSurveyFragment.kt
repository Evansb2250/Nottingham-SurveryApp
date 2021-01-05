package com.example.surveyapp.fragments.createTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.databinding.FragmentCreateSurveyBinding

// TODO: Rename parameter arguments, choose names that match

class CreateSurveyFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var binding: FragmentCreateSurveyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_survey, container, false)
        // Inflate the layout for this fragment

        binding.capitalRB.setOnClickListener {
            SurveyActivity.createSurveyPage?.setSurveryType(constant.CAPTIAL)
            Toast.makeText(
                requireContext(),
                SurveyActivity.createSurveyPage?.getData(),
                Toast.LENGTH_LONG
            ).show()
        }

        binding.revenueRB.setOnClickListener {
            SurveyActivity.createSurveyPage?.setSurveryType(constant.REVENUE)
            Toast.makeText(
                requireContext(),
                SurveyActivity.createSurveyPage?.getData(),
                Toast.LENGTH_LONG
            ).show()
        }



        binding.nameEdit.addTextChangedListener { text ->
            SurveyActivity.createSurveyPage?.storeName(text.toString())
        }

        binding.addressEdit.addTextChangedListener { text ->
            SurveyActivity.createSurveyPage?.storeAddress(text.toString())
        }

        binding.postEdit.addTextChangedListener { text ->
            SurveyActivity.createSurveyPage?.storePostCode(text.toString())
        }

        binding.phoneNumEdit.addTextChangedListener { text ->
            SurveyActivity.createSurveyPage?.storePhoneNumber(text.toString())
        }

        binding.dateEdit.addTextChangedListener { text ->
            SurveyActivity.createSurveyPage?.storeDate(text.toString())
        }









        return binding.root
    }

}