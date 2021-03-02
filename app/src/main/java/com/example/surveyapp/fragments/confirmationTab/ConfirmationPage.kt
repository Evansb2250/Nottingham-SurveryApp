package com.example.surveyapp.fragments.confirmationTab


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.surveyapp.R
import com.example.surveyapp.activities.MainActivity
import com.example.surveyapp.activities.SurveyActivity.Companion.confirmPage
import com.example.surveyapp.databinding.FragmentConfirmationPageBinding
import com.itextpdf.text.*
import java.io.*
import java.text.DecimalFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [confirmationPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfirmationPage : Fragment() {

    private val currency = DecimalFormat("£###,###.##")

    lateinit var binding: FragmentConfirmationPageBinding
    val STORAGE_CODE = 100

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("WrongThread")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirmation_page, container, false)
        binding.textField.isFocusable = false

        binding.viewModel = confirmPage
        binding.lifecycleOwner = this

        buttonListener(binding.button2)
        setUpAsCSV(binding.button4)
        setUpPDFButton(binding.pdfButton)
        saveAndExit(binding.cancelButton)
        setUpCheckButton(binding.showPriceCheckBox)
        exitDontSave(binding.button5)


        confirmPage?.total?.observe(viewLifecycleOwner, { newAmount ->
            binding.total.text = currency.format(newAmount)
        })

        confirmPage?.rechargeTotal?.observe(viewLifecycleOwner, { newAmount ->
            binding.rechargeAmount.text = currency.format(newAmount)
        })

        confirmPage?.message?.observe(viewLifecycleOwner, { newMessage ->
            binding.textField.text.clear()
            binding.textField.setText(newMessage)
        })

        binding.taxPrecentage.addTextChangedListener { pct ->
            val percentage = checkTaxPercentage(pct.toString())
            confirmPage?.changeVAT(percentage.toDouble())

        }


        return binding.root
    }



    private fun checkTaxPercentage(pct: String): String{
        var percentage = pct
        if (percentage.equals("")) {
            percentage = "0.0"
            binding.taxPrecentage.setText("")
        } else if (percentage.toDouble() > 100) {
            Toast.makeText(
                requireContext(),
                "Percentages can only be between 0 - 100",
                Toast.LENGTH_SHORT
            ).show()
            binding.taxPrecentage.text.clear()
            binding.taxPrecentage.setText("20")

            //Sets the default VAT RATE
            confirmPage?.changeVAT(20.0)

        }
        return percentage
    }

    private fun exitDontSave(exitButton: Button) {
        exitButton.setOnClickListener{
            exitActivity(requireContext(), activity)
        }
    }


    private fun setUpCheckButton(showPriceCheckBox: CheckBox) {
        showPriceCheckBox.setOnClickListener { it ->

            confirmPage?.changeShowPriceStatus(showPriceCheckBox.isChecked)
            pullDataAndDisplay()
            // Toast.makeText(requireContext(), "Recalculate", Toast.LENGTH_SHORT).show()

        }
    }


    private fun saveAndExit(cancelButton: Button) {
        cancelButton.setOnClickListener { it ->
            pullDataAndDisplay()
            confirmPage?.insertCompleteSurvey()
            exitActivity(requireContext(), activity)

        }
    }

        @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpPDFButton(pdfButton: Button) {
        pdfButton.setOnClickListener { it ->
            checkPerm()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPerm() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            val context = context
            if (context != null) {
                if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, STORAGE_CODE)
                } else
                    displayPDFStatus(confirmPage?.savePdfHandler()!!)
            }
        } else {
            displayPDFStatus(confirmPage?.savePdfHandler()!!)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            STORAGE_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    displayPDFStatus(confirmPage?.savePdfHandler()!!)
                } else {
                    Toast.makeText(requireContext(), "Permission denied .....!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }






    fun setUpAsCSV(pdfButton: Button) {
        pdfButton.setOnClickListener { it ->
            try {
                val file = File(activity?.getExternalFilesDir(null), "testfile.txt")
                val fileOutput = FileOutputStream(file)
                val outputStreamWriter = OutputStreamWriter(fileOutput)
                val message = confirmPage?.getList()
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
            /**** TODO remove this code once code works again ***/

            /*** NEW CODE   ***/
            pullDataAndDisplay()

        }

    }


    private fun displayPDFStatus(pdfWasCreatead: Boolean){
        if(pdfWasCreatead){
            Toast.makeText(requireContext(),"PDF Saved",Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(requireContext(),"PDF Failed " + PdfCreator.message,Toast.LENGTH_SHORT).show()
        }
    }





    private fun pullDataAndDisplay() {
        confirmPage?.getData()
        if (confirmPage?._dataFromSurvey != null) {
            confirmPage?.updateTotal()
        } else
            Toast.makeText(requireContext(), "Error Occurred!!.", Toast.LENGTH_LONG).show()
    }
}



private fun exitActivity(activityContext: Context, activity: FragmentActivity?) {
    val intent = Intent(activityContext, MainActivity::class.java)
    activity!!.startActivity(intent)
    activity!!.finish()
}



fun returnAnyNumberWithoutError(numberToCheck: String): Double {
    var testNumber = numberToCheck
    if (testNumber.equals("")) {
        testNumber = "0.0"
        return testNumber.toDouble()
    }
    return testNumber.toDouble()
}



