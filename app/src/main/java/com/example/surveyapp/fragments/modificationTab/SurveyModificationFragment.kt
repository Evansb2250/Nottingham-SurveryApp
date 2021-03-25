package com.example.surveyapp.fragments.modificationTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.surveyapp.R
import com.example.surveyapp.application.SurveyApplication
import com.example.surveyapp.databinding.FragmentSurveyModificationBinding
import com.example.surveyapp.fragments.viewSurvey.ViewSurveyViewModel
import com.example.surveyapp.fragments.viewSurvey.ViewSurveyViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [SurveyModificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SurveyModificationFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var binding:FragmentSurveyModificationBinding

    private val viewModel: SurveyModificationViewModel by viewModels {
        SurveyModificationViewModelFactory((requireActivity().application as SurveyApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_modification, container,false)


        viewModel.loadedSorCode.observe(viewLifecycleOwner, Observer { currentSorCode ->
            if(currentSorCode != null) {
                clearAll()
                lockTextEdits()
                binding.sorCodeEdit.setText(currentSorCode.sorCode)
                binding.descriptionEdit.setText(currentSorCode.description)
                binding.uomEdit.setText(currentSorCode.UOM)
                binding.rechargeRateEdit.setText(currentSorCode.rechargeRate.toString())
            }

        })


        viewModel.errorDetected.observe(viewLifecycleOwner, Observer { error ->
            if(error){
            Toast.makeText(requireContext(),viewModel.getErrorMessageInApp(), Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(requireContext(),viewModel.getGenericMessage(), Toast.LENGTH_LONG).show()
                 loadSoRCodeBackIntoSearchField()
            }
        })




        binding.imageButtonRequestSearch.setOnClickListener{
            val sorCode = binding.sorCodeSearchField.getText().toString().toUpperCase()
            viewModel.querySoRCodeRequestFromDB(sorCode)

            binding.sorCodeSearchField.setText("")
        }


        binding.editSorCodeButton.setOnClickListener{
            unlockTextEdits()
        }


        binding.updateSorCodeButton.setOnClickListener{
            val description = binding.descriptionEdit.getText().toString()
            val uom = binding.uomEdit.getText().toString()
            val rechargeRate = binding.rechargeRateEdit.getText().toString()
            viewModel.checkUpDateRequest(description, uom, rechargeRate)
            clearAll()
            lockTextEdits()
        }








        return binding.root
    }

    private fun loadSoRCodeBackIntoSearchField() {
        if(viewModel.loadedSorCode.value != null) {
            binding.sorCodeSearchField.setText(viewModel.loadedSorCode.value?.sorCode)
            viewModel.setLoadSorToNull()
        }
    }

    private fun lockTextEdits() {
        binding.sorCodeEdit.isFocusable= false
        binding.descriptionEdit.isFocusable= false
        binding.uomEdit.isFocusable= false
        binding.rechargeRateEdit.isFocusable= false
    }


    private fun unlockTextEdits() {
        binding.descriptionEdit.isFocusableInTouchMode= true
        binding.uomEdit.isFocusableInTouchMode = true
        binding.rechargeRateEdit.isFocusableInTouchMode = true
    }



    private fun clearAll() {
        binding.sorCodeEdit.setText("")
        binding.descriptionEdit.setText("")
        binding.uomEdit.setText("")
        binding.rechargeRateEdit.setText("")
    }


}