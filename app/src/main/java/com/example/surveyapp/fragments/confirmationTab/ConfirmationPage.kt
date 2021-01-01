package com.example.surveyapp.fragments.confirmationTab

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.databinding.FragmentConfirmationPageBinding

import com.example.surveyapp.domains.SurveySORs
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import java.io.*

import java.text.DecimalFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [confirmationPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfirmationPage : Fragment() {


    lateinit var binding: FragmentConfirmationPageBinding


    @SuppressLint("WrongThread")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_confirmation_page, container, false)
        binding.textField.isFocusable = false
        val currency = DecimalFormat("£###,###.##")

        binding.viewModel = SurveyActivity.confirmPage
        binding.lifecycleOwner = this

        buttonListener(binding.button2)
        setUpPdfButton(binding.button4)
        setUpCancelButton(binding.cancelButton)

//
//        SurveyActivity.confirmPage?.DataFromSurvey?.observe(viewLifecycleOwner, { list ->
//            Toast.makeText(requireContext(), "Observer act", Toast.LENGTH_LONG).show()
//            SurveyActivity.confirmPage!!.updateTotal(list)
//        })

        SurveyActivity.confirmPage?.total?.observe(viewLifecycleOwner, { newAmount ->
            binding.total.text = currency.format(newAmount)
        })

        SurveyActivity.confirmPage?.message?.observe(viewLifecycleOwner, { newMessage ->
            binding.textField.text.clear()
            binding.textField.setText(newMessage)
        })




        return binding.root
    }


    private fun setUpCancelButton(cancelButton: Button) {
        cancelButton.setOnClickListener { it ->
            activity?.finish()

        }
    }


    fun setUpPdfButton(pdfButton: Button) {
        pdfButton.setOnClickListener { it ->
            try {

                val filename = "myfile"
                val fileContents = "Hello world!"
                val file = File(context?.filesDir, filename)
                context?.openFileOutput(filename, Context.MODE_PRIVATE).use {
                    if (it != null) {
                        it.write(fileContents.toByteArray())
                        it.close()
                    }
                }

                Toast.makeText(requireContext(), "File Created", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(
                    requireContext(),
                    "ExceptionFile write failed: " + e.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun buttonListener(button2: Button) {

        button2.setOnClickListener { it ->
            Toast.makeText(requireContext(), "Loading.....", Toast.LENGTH_SHORT).show()
            val list = SurveyActivity.confirmPage?.getData()
            if (list != null) {
                SurveyActivity.confirmPage!!.updateTotal(list)
            } else
                Toast.makeText(requireContext(), "Error Occurred!!.", Toast.LENGTH_LONG).show()


        }

    }


}





