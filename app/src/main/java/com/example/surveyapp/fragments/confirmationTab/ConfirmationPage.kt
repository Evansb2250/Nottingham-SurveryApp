package com.example.surveyapp.fragments.confirmationTab


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.surveyapp.R
import com.example.surveyapp.activities.MainActivity
import com.example.surveyapp.activities.SurveyActivity
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


        confirmPage?.surveyIDTextView?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { SurveyID ->
            binding.idLabel.text = SurveyID.toString()
        })
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

        confirmPage?.refreshFinished?.observe(viewLifecycleOwner, { newChange ->
       //     Toast.makeText(requireContext(), "Change Detected", Toast.LENGTH_SHORT).show()
            confirmPage!!.updateTotal()
        })

        confirmPage?.canSuveryBeSaved?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { status ->
            if(status == true){
                exitActivity(requireContext(), activity)
            }
        })

        confirmPage?.statusMessage?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { status ->
            Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
        })


confirmPage?.updateDetected?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { update ->
    if(confirmPage?.VAT?.value != null){
        val percentage = confirmPage?.VAT?.value!! * 100
        binding.taxPrecentage.setText(percentage.toString())
    }
})

        binding.taxPrecentage.addTextChangedListener { pct ->
            val percentage = checkTaxPercentage(pct.toString())
            confirmPage?.changeVAT(percentage.toDouble())
        }


        buttonListener(binding.refreshPageButton)
        setUpPDFButton(binding.pdfButton)
        saveAndExit(binding.cancelButton)
        setUpCheckButton(binding.showPriceCheckBox)
        exitDontSave(binding.button5)



        return binding.root
    }



    private fun checkTaxPercentage(pct: String): String{
        var percentage = pct
        if (percentage.equals("")) {
            percentage = "0.0"
            binding.taxPrecentage.setText(percentage)
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
            SurveyActivity.isApplicationInEditMode = false
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
                if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    Toast.makeText(requireContext(), "Creating PDF", Toast.LENGTH_LONG).show()
                    requestPermissions(permissions, STORAGE_CODE)
                }
            }else
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_LONG).show()
        } else {
            confirmPage?.savePdfHandler()
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
                    confirmPage?.savePdfHandler()
                } else {
                    Toast.makeText(requireContext(), "Permission denied .....!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }




    private fun buttonListener(refreshPageButton: Button) {
        refreshPageButton.setOnClickListener { it ->
       //    Toast.makeText(requireContext(), "Loading.....", Toast.LENGTH_SHORT).show()
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
    }


}

private fun exitActivity(activityContext: Context, activity: FragmentActivity?) {
    val intent = Intent(activityContext, MainActivity::class.java)
    activity!!.startActivity(intent)

    activity!!.finish()
}




