package com.example.surveyapp.fragments.viewSurvey

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.application.SurveyApplication
import com.example.surveyapp.databinding.FragmentViewSurvey2Binding

import java.text.DecimalFormat


class ViewSurveyFragment : Fragment() {

    private lateinit var binding: FragmentViewSurvey2Binding
    private val currency = DecimalFormat("£###,###.##")

    private val viewModel: ViewSurveyViewModel by viewModels {
        ViewSurveyViewModelFactory((requireActivity().application as SurveyApplication).repository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_view_survey2, container, false)

//

        binding.imageButtonRequestSearch.setOnClickListener {
            val searchMethod = binding.searchBySpinner.getSelectedItem()
            val surveyDetail = binding.searchEditField.getText().toString()
            viewModel.requestSurvey(searchMethod, surveyDetail)

        }
//
        binding.deleteSurveyButton.setOnClickListener {
            if (viewModel.currentSurvey.value != null) {

                // Toast.makeText(requireContext(), viewModel.position.toString(), Toast.LENGTH_LONG).show()
                viewModel.deleteSurvey()
                clearDescriptionField()
            } else
                Toast.makeText(requireContext(), "Must select a survey", Toast.LENGTH_LONG).show()
            resetSearchField()
        }

        viewModel.id.observe(viewLifecycleOwner, Observer { id ->
            Toast.makeText(requireContext(), "Last ID " + id.toString(), Toast.LENGTH_LONG).show()
        })
//
//
        viewModel.validResponse.observe(viewLifecycleOwner, Observer { flag ->
            if (!flag) {
                Toast.makeText(requireContext(), viewModel.errorMessage, Toast.LENGTH_LONG).show()
            }
            resetSearchField()
        })



        viewModel.currentSurvey.observe(viewLifecycleOwner, Observer { survey ->
            if (survey != null) {
                binding.surveyIDEdit.setText(survey.surveyId.toString())
                binding.nameEdit.setText(survey.surveyorName)
                binding.dateEdit.setText("${survey.day}/${survey.month}/${survey.year}")
                binding.surveyTypeEdit.setText(survey.surveyType)
                binding.addressEdit.setText(survey.address)
                binding.postCodeEdit.setText(survey.postCode)
                binding.surveyTotalEdit.setText(currency.format(survey.surveyTotal))
                binding.rechargeAmountEdit.setText(currency.format(survey.rechargeTotal))

            } else
                clearDescriptionField()

        })



        binding.listView.setOnItemClickListener { parent, view, position, id ->
            viewModel.changeCurrentSurvey(position)
        }

//        viewModel.currentSurvey.observe(viewLifecycleOwner, Observer { survey ->
//            if (survey != null) {
//                val details =
//                    " Survey ID: ${survey.surveyId} \n\n Name: ${survey.surveyorName}\n\n Date: ${survey.day}/${survey.month}/${survey.year}\n\n" +
//                            " Survey Type: ${survey.surveyType}\n\n Address: ${survey.address}\n\n Post Code: ${survey.postCode}\n\n " +
//                            "Survey Total: ${currency.format(survey.surveyTotal)}\n\n Recharge Total: ${
//                                currency.format(
//                                    survey.rechargeTotal
//                                )
//                            } "
//
//                binding.descriptionBox.setText(details)
//            } else
//                binding.descriptionBox.setText("")
//
//        })
//        binding.listView.setOnItemClickListener { parent, view, position, id ->
//            viewModel.changeCurrentSurvey(position)
//        }
//
//

        viewModel.changeDetected.observe(viewLifecycleOwner, Observer { flag ->
            setUpListView(viewModel.listOfSurveyTitles)
            resetSearchField()
        })


        binding.editSurveyButton.setOnClickListener { it ->

            if (viewModel.currentSurvey.value != null) {
                val survey = viewModel.currentSurvey.value
                val intent = Intent(activity, SurveyActivity::class.java)
                intent.putExtra(constant.bundleMessage, constant.editSurveyTag)
                intent.putExtra(constant.idTag, survey!!.surveyId.toString())
                requireActivity().startActivity(intent)
                activity?.finish()
            } else
                Toast.makeText(
                    requireContext(),
                    "Please select a survey before attempting to edit!",
                    Toast.LENGTH_LONG
                ).show()

        }


        return binding.root
    }

    //
    private fun clearDescriptionField() {
        binding.surveyIDEdit.setText("")
        binding.nameEdit.setText("")
        binding.dateEdit.setText("")
        binding.surveyTypeEdit.setText("")
        binding.addressEdit.setText("")
        binding.postCodeEdit.setText("")
        binding.surveyTotalEdit.setText("")
        binding.rechargeAmountEdit.setText("")
    }

    //
    private fun resetSearchField() {
        binding.searchEditField.setText("")
    }


    private fun setUpListView(list: List<String>) {
        binding.listView.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            list.toList()
        )


    }


}