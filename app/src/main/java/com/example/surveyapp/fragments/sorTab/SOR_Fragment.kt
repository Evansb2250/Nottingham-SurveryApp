package com.example.surveyapp.fragments.sorTab

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.surveyapp.CONSTANTS.constant
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



        SurveyActivity.sorViewModel?.sorDescripition?.observe(
            viewLifecycleOwner,
            Observer { newSor ->
                binding.sorDescriptionBox.text.clear()
                binding.sorDescriptionBox.append(newSor.toString())
            })



        setupImageButton(binding)
        setUpSpinner(binding)


        // TODO create observers for noticing the change
        //TODO plan which variable type will be needed inside


        return binding.root
    }

    private fun setupImageButton(binding: FragmentSORBinding) {
        binding.imageButton.setOnClickListener({ it ->
            val userInput = binding.searchView.text.toString().trim()
            SurveyActivity.sorViewModel?.get(userInput.toUpperCase())
            binding.searchView.text.clear()
            alertUser(binding)


        })
    }


    private fun alertUser(binding: FragmentSORBinding) {
        if (binding.viewmodel!!.searchWasFound) {
            Toast.makeText(requireContext(), "Search found", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(requireContext(), "Nothing found", Toast.LENGTH_SHORT).show()

    }


    private fun setUpSpinner(binding: FragmentSORBinding) {
        val arrayAdapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            constant.searchBy.toList()
        )
        binding.optionSelector.adapter = arrayAdapter
        binding.optionSelector.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.viewmodel?.searchby?.value = constant.searchBy[position]

            }


        }

    }


}