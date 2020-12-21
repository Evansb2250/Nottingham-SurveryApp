package com.example.surveyapp.fragments.sorTab

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.surveyapp.databinding.FragmentSORBinding
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity


/**
 * A simple [Fragment] subclass.
 * Use the [SOR_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SOR_Fragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Instantiate the binding
        val binding: FragmentSORBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_s_o_r_, container, false)

        // Bind the binindingViewModel variable with the global viewModel
        binding.viewmodel = SurveyActivity.sorViewModel
        binding.lifecycleOwner = this



        SurveyActivity.sorViewModel?.searchResult?.observe(viewLifecycleOwner, Observer { newSor ->
            binding.sorDescriptionBox.text.clear()
            binding.sorDescriptionBox.append(newSor.toString())
        })


        // TODO create observers for noticing the change
        //TODO plan which variable type will be needed inside


        return binding.root
    }







}