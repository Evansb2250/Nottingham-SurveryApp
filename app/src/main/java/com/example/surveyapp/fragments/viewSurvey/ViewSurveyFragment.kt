package com.example.surveyapp.fragments.viewSurvey

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.application.SurveyApplication
import com.example.surveyapp.databinding.FragmentViewSurveyBinding


class ViewSurveyFragment : Fragment() {

    private lateinit var binding: FragmentViewSurveyBinding

    private val viewModel: ViewSurveyViewModel by viewModels {
        ViewSurveyViewModelFactory((requireActivity().application as SurveyApplication).repository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_survey, container, false)



        binding.imageButtonRequestSearch.setOnClickListener {
            val searchMethod = binding.searchBySpinner.getSelectedItem()
            val surveyDetail = binding.searchEditField.getText().toString()
            viewModel.requestSurvey(searchMethod, surveyDetail)

        }







        viewModel.id.observe(viewLifecycleOwner, Observer { id ->
            Toast.makeText(requireContext(), "Last ID " + id.toString(), Toast.LENGTH_LONG).show()
        })

        viewModel.currentSurvey.observe(viewLifecycleOwner, Observer { survey->

            if(survey!= null){
                val  intent  = Intent(activity, SurveyActivity::class.java)
                intent.putExtra(constant.bundleMessage,constant.editSurveyTag)
                intent.putExtra(constant.idTag, survey.surveyId.toString())
                requireActivity().startActivity(intent)
                activity?.finish()
            }
          //  Toast.makeText(requireContext(), survey.surveyorName, Toast.LENGTH_LONG).show()
        })


        return binding.root
    }


}