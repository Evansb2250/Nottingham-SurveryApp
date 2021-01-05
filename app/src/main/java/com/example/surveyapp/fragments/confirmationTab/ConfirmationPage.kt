package com.example.surveyapp.fragments.confirmationTab

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.databinding.FragmentConfirmationPageBinding
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

        SurveyActivity.confirmPage?.rechargeTotal?.observe(viewLifecycleOwner, { newAmount ->
            binding.rechargeAmount.text = currency.format(newAmount)
        })


        SurveyActivity.confirmPage?.message?.observe(viewLifecycleOwner, { newMessage ->
            binding.textField.text.clear()
            binding.textField.setText(newMessage)
        })

        binding.taxPrecentage.addTextChangedListener { pct ->
            var percentage = pct.toString()
            if (percentage.equals("")) {
                percentage = "0.0"
            }
            SurveyActivity.confirmPage?.changeVAT(percentage.toDouble())

//            SurveyActivity.confirmPage?.changePercentage()

        }




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
                val file = File(activity?.getExternalFilesDir(null), "testfile.txt")
                val fileOutput = FileOutputStream(file)
                val outputStreamWriter = OutputStreamWriter(fileOutput)
                val message = SurveyActivity.confirmPage?.getList()
                if (message != null) {
                    for (sorDetails in message) {
                        outputStreamWriter.write(sorDetails)
                    }
                }
                outputStreamWriter.flush()
                fileOutput.fd.sync()
                outputStreamWriter.close()
                Toast.makeText(requireContext(), "Pased ", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                Toast.makeText(
                    requireContext(),
                    "Failed " + e.message.toString(),
                    Toast.LENGTH_LONG
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





