package com.example.surveyapp.fragments.asbestoRomval

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.databinding.FragmentAsbestoRemovalBinding
import com.example.surveyapp.databinding.FragmentConfirmationPageBinding

// TODO: Rename parameter arguments, choose names that match



class AsbestoRemoval : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var binding: FragmentAsbestoRemovalBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_asbesto_removal, container, false)


        // updates text
        binding.abestoRemovalEditView.addTextChangedListener{
            val description = binding.abestoRemovalEditView.getText().toString()
            SurveyActivity.abesto!!.updateText(description)
        }
        //



        return binding.root
    }

}