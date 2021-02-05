package com.example.surveyapp.fragments.viewSurvey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.example.surveyapp.R
import com.example.surveyapp.application.SurveyApplication
import com.example.surveyapp.databinding.FragmentViewSurveyBinding
import com.example.surveyapp.repository.DatabaseRepository


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



        binding.imageButton2.setOnClickListener(
            {
               // Toast.makeText(requireContext(), "YOU CLICKED", Toast.LENGTH_LONG).show()
                viewModel.createMessage()
            }
        )



        return binding.root
    }


}