package com.example.surveyapp.fragments.sorTab

import android.annotation.SuppressLint
import android.app.Application
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
import androidx.activity.viewModels
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.databinding.FragmentSORBinding
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.application.SurveyApplication
import java.text.DecimalFormat


/**
 * A simple [Fragment] subclass.
 * Use the [SOR_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SOR_Fragment : Fragment() {
    private val application = SurveyApplication()
    private val currency = DecimalFormat("$###,###.##")
    private lateinit var viewModel: SurveySorViewModel

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("SystemOutPut", " Destroying view")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Instantiate the binding
        val binding: FragmentSORBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_s_o_r_, container, false)

        // Create the viewModelFactory
        val viewModelFactory = SurveySorViewModelFactory((application).repository)
        //Instantiate the viewModel using the viewModelFactory
        viewModel = viewModelFactory.create(SurveySorViewModel::class.java)

//        val viewModel: SurveySorViewModel by viewModels { SurveySorViewModelFactory((application).repository) }

        Log.i("SystemOutPut", "On create called")

        // TEST
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.quantitySelected.observe(viewLifecycleOwner, Observer {
            viewModel.updateTotalByQuantity()

        })

        viewModel.total.observe(viewLifecycleOwner, Observer {

        })




        setupImageButton(binding)
        setUpSpinner(binding)
        setUpNumberSpinner(binding)


        // TODO create observers for noticing the change
        //TODO plan which variable type will be needed inside


        return binding.root
    }


    private fun setupImageButton(binding: FragmentSORBinding) {
        binding.imageButton.setOnClickListener({ it ->
            val userInput = binding.searchView.text.toString().trim()
            binding.viewmodel?.searchFor(userInput.toUpperCase())
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


    private fun setUpNumberSpinner(binding: FragmentSORBinding) {
        val numberArrayAdapter = ArrayAdapter(
            requireActivity(), android.R.layout.simple_spinner_dropdown_item,
            constant.quantityRange.toList()
        )

        binding.quantitySpinner.adapter = numberArrayAdapter

        //adding listener
        binding.quantitySpinner.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                binding.viewmodel!!.quantitySelected.value = constant.quantityRange[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
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

    private fun updateTotal(binding: FragmentSORBinding) {
        binding.totalTextView.text.clear()
        binding.totalTextView.append(currency.format(viewModel.total.value))
    }


}