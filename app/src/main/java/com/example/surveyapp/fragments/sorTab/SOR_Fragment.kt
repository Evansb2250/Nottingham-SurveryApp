package com.example.surveyapp.fragments.sorTab

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
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
    private val currency = DecimalFormat("£###,###.##")

    // private lateinit var `SurveyActivity.sorViewModel`: SurveySorViewModel
    private lateinit var binding: FragmentSORBinding

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("SystemOutPut", " Destroying view")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Instantiate the binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_s_o_r_, container, false)


        // TEST
        binding.viewmodel = SurveyActivity.sorViewModel
        binding.lifecycleOwner = this
        binding.sorIdentifier.visibility = View.INVISIBLE


        SurveyActivity.sorViewModel!!.quantitySelected.observe(viewLifecycleOwner, Observer {
            SurveyActivity.sorViewModel!!.updateTotalByQuantity()
            updateTotal()

        })

        SurveyActivity.sorViewModel!!.total.observe(viewLifecycleOwner, Observer {
            updateTotal()

        })

        SurveyActivity.sorViewModel!!.rechargeAmount.observe(
            viewLifecycleOwner,
            Observer { rechargeAmount ->
                binding.rechargeTextView.text.clear()
                binding.rechargeTextView.append(currency.format(rechargeAmount))
            })


        setUpListView()
        setupImageButton()
        setUpSpinner()
        setUpNumberSpinner()


        // TODO create observers for noticing the change
        //TODO plan which variable type will be needed inside


        return binding.root
    }

    private fun setUpListView() {
        binding.searchResultBox.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            SurveyActivity.sorViewModel!!.listForView.toList()
        )
    }

    private fun restoreUI() {
        binding.sorIdentifier.text = SurveyActivity.sorViewModel?.searchViewEntry
        binding.sorIdentifier.visibility = View.VISIBLE
    }


    private fun setupImageButton() {
        binding.imageButton.setOnClickListener({ it ->
            SurveyActivity.sorViewModel?.searchViewEntry = binding.searchView.text.toString().trim()
            binding.viewmodel?.searchFor(SurveyActivity.sorViewModel!!.searchViewEntry)
            binding.sorIdentifier.text = SurveyActivity.sorViewModel!!.searchViewEntry
            binding.sorIdentifier.visibility = View.VISIBLE
            binding.searchView.text.clear()
            binding.quantitySpinner.setSelection(0)

            if (binding.optionSelector.selectedItem == constant.KEYWORD) {
                setUpListView()
            }


            alertUser()


        })
    }


    private fun alertUser() {
        if (binding.viewmodel!!.searchWasFound) {
            Toast.makeText(requireContext(), "Search found", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(requireContext(), "Nothing found", Toast.LENGTH_SHORT).show()

    }


    private fun setUpNumberSpinner() {
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


    private fun setUpSpinner() {
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

    private fun updateTotal() {
        binding.totalTextView.text.clear()
        binding.totalTextView.append(currency.format(SurveyActivity.sorViewModel!!.total.value))
    }


}