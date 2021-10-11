package com.example.surveyapp.fragments.createTab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.activities.SurveyActivity.Companion.createSurveyPage
import com.example.surveyapp.databinding.FragmentCreateSurveyBinding
import java.text.SimpleDateFormat
import java.util.*

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







        binding.calendarView.setOnDateChangeListener{CalendarView, year, month, dayOfMonth ->
            createSurveyPage?.month_?.value = month+1
            createSurveyPage?.year_?.value = year
            createSurveyPage?.day_?.value = dayOfMonth

        }






        return binding.root
    }

}