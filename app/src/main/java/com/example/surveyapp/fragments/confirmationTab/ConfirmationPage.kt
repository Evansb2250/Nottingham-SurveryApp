package com.example.surveyapp.fragments.confirmationTab


import android.Manifest
import android.annotation.SuppressLint
import android.app.usage.UsageEvents.Event.NONE
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
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
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.CONSTANTS.constant.Companion.HEADERS1
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.activities.SurveyActivity.Companion.confirmPage
import com.example.surveyapp.databinding.FragmentConfirmationPageBinding
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_confirmation_page, container, false)
        binding.textField.isFocusable = false
        val currency = DecimalFormat("£###,###.##")

        binding.viewModel = confirmPage
        binding.lifecycleOwner = this

        buttonListener(binding.button2)
        setUpAsCSV(binding.button4)
        setUpPDFButton(binding.pdfButton)

        setUpCancelButton(binding.cancelButton)
        setUpCheckButton(binding.showPriceCheckBox)

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
            var percentage = pct.toString()
            if (percentage.equals("")) {
                percentage = "0.0"
                binding.taxPrecentage.setText("0")
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
            confirmPage?.changeVAT(percentage.toDouble())

        }




        return binding.root
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
                    savePdf()
            }
        } else {
            savePdf()
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
                    savePdf()
                } else {
                    Toast.makeText(requireContext(), "Permission denied .....!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    private fun savePdf() {
        // Create object of Document class
        val mDoc = Document(PageSize.A3.rotate())
        //    val mDoc = Document(PageSize.A4.rotate())
        //pdf file name
        val mFileName = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(System.currentTimeMillis())
        // PDF file path
        val mFilePath =
            Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
        try {
            //create instance of pdfwriter class
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

            //open the document for writing
            mDoc.open()


            //val pointColumnWidths = FloatArray(150)
            val table = PdfPTable(10)

            /*
            1.Revenue
            2.response
            3.Capital
            4.response
            5.Fire / services referral
            6.reach
            7.Rechargeable Total
            8.amount
             */



            for (id in 0..9) {
                val phrase = Phrase()

                if (id == 1 || id == 4 || id == 9) {

                    if (id == 1) {
                        constant.HEADERS1[1] = confirmPage?.address.toString()
                        phrase.add(
                            Chunk(
                                HEADERS1[id],
                                FontFactory.getFont(FontFactory.HELVETICA)
                            )
                        )
                    }
                    if (id == 4) {
                        constant.HEADERS1[4] = confirmPage?.name.toString()
                        phrase.add(
                            Chunk(
                                HEADERS1[id],
                                FontFactory.getFont(FontFactory.HELVETICA)
                            )
                        )
                    }

                    if (id == 9) {

                        if (confirmPage?.hidePrices!!.equals(true)) {
                            HEADERS1[id] = currency.format(0.0)
                        }

                        if (confirmPage?.hidePrices!!.equals(false) && confirmPage!!.surveyType.equals(
                                constant.REVENUE
                            )
                        ) {
                            HEADERS1[id] = currency.format(confirmPage?.total?.value)
                        } else {
                            HEADERS1[id] = currency.format(0.0)
                        }

                        phrase.add(Chunk(HEADERS1[id], FontFactory.getFont(FontFactory.HELVETICA)))
                    }

                } else
                    phrase.add(Chunk(HEADERS1[id], FontFactory.getFont(FontFactory.TIMES_BOLD)))


                val cell = PdfPCell(phrase)
                cell.border = NONE
                table.addCell(cell)
            }






            for (id in 0..9) {
                val phrase = Phrase()

                if (id == 1) {
                    if (confirmPage!!.surveyType.equals(constant.REVENUE)) {
                        constant.HEADERS2[id] = "Y"
                    } else
                        constant.HEADERS2[id] = ""
                }
                if (id == 3) {
                    if (confirmPage!!.surveyType.equals(constant.CAPTIAL)) {
                        constant.HEADERS2[id] = "Y"
                    } else
                        constant.HEADERS2[id] = ""
                }

                if (id == 9) {
                    if (confirmPage?.hidePrices!!.equals(true)) {
                        constant.HEADERS2[id] = currency.format(0.0)
                    }

                    if (confirmPage?.hidePrices!!.equals(false) && confirmPage!!.surveyType.equals(
                            constant.CAPTIAL
                        )
                    ) {
                        constant.HEADERS2[id] = currency.format(confirmPage?.total?.value)
                    } else {
                        constant.HEADERS2[id] = currency.format(0.0)
                    }

                    phrase.add(
                        Chunk(
                            constant.HEADERS2[id],
                            FontFactory.getFont(FontFactory.HELVETICA)
                        )
                    )
                } else
                    phrase.add(
                        Chunk(
                            constant.HEADERS2[id],
                            FontFactory.getFont(FontFactory.TIMES_BOLD)
                        )
                    )

                //  phrase.add(Chunk(constant.HEADERS2[id], FontFactory.getFont(FontFactory.TIMES_BOLD)))
                val cell = PdfPCell(phrase)
                cell.border = NONE
                table.addCell(cell)
            }

//
            for (id in 0..9) {

                val phrase = Phrase()

                if (id == 9) {
                    constant.HEADERS3[id] = currency.format(confirmPage?.rechargeTotal?.value)
                    phrase.add(
                        Chunk(
                            constant.HEADERS3[id],
                            FontFactory.getFont(FontFactory.HELVETICA)
                        )
                    )
                } else
                    phrase.add(
                        Chunk(
                            constant.HEADERS3[id],
                            FontFactory.getFont(FontFactory.TIMES_BOLD)
                        )
                    )

                val cell = PdfPCell(phrase)
                cell.border = NONE
                table.addCell(cell)
            }

            //UNDERLINE
            val cell = PdfPCell(Phrase(""))
            cell.colspan = 10
            table.addCell(cell)

            val list = confirmPage?.getSORS()
            if (list != null) {
                var cell: PdfPCell

                for (sor in list) {
                    //DIVIDER


                    var phrase = Phrase()
                    phrase.add(
                        Chunk(
                            sor.roomCategory,
                            FontFactory.getFont(FontFactory.TIMES_ITALIC)
                        )
                    )
                    cell = PdfPCell(phrase)
                    cell.border = NONE
                    cell.fixedHeight = 10F
                    table.addCell(cell)

                    cell = PdfPCell(Phrase(sor.sorCode))
                    cell.border = NONE
                    table.addCell(cell)

                    cell = PdfPCell(Phrase(sor.sorDescription))
                    cell.border = NONE
                    table.addCell(cell)

                    phrase = Phrase()
                    phrase.add(Chunk(sor.UOM, FontFactory.getFont(FontFactory.TIMES_ITALIC)))
                    cell = PdfPCell(phrase)
                    cell.border = NONE
                    table.addCell(cell)


                    cell = PdfPCell(Phrase(sor.quantity.toString()))
                    cell.border = NONE
                    table.addCell(cell)

                    cell = PdfPCell(Phrase(recharge(sor.isRecharge)))
                    cell.border = NONE
                    table.addCell(cell)



                    cell = PdfPCell(Phrase(currency.format(hidePrices(sor.total))))
                    cell.border = NONE
                    table.addCell(cell)

                    cell = PdfPCell(Phrase(sor.surveyorDescription))
                    cell.border = NONE
                    table.addCell(cell)

                    cell = PdfPCell(Phrase(""))
                    cell.border = NONE
                    table.addCell(cell)

                    cell = PdfPCell(Phrase(""))
                    cell.border = NONE
                    table.addCell(cell)

                    cell = PdfPCell(Phrase(""))
                    cell.border = NONE
                    cell.colspan = 10
                    table.addCell(cell)
                }
            }

            mDoc.add(table)






            mDoc.close()

            Toast.makeText(
                requireContext(),
                "$mFileName.pdf\nis saved to\n$mFilePath",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            //
            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun recharge(isRecharge: Boolean): String {
        if (isRecharge.equals(true)) {
            return "Y"
        } else return ""
    }


    private fun setUpCheckButton(showPriceCheckBox: CheckBox) {
        showPriceCheckBox.setOnClickListener { it ->

            confirmPage?.changeShowPriceStatus(showPriceCheckBox.isChecked)
            pullDataAndDisplay()
            // Toast.makeText(requireContext(), "Recalculate", Toast.LENGTH_SHORT).show()

        }
    }


    private fun setUpCancelButton(cancelButton: Button) {
        cancelButton.setOnClickListener { it ->
            SurveyActivity.confirmPage?.insertCompleteSurvey()
            activity?.finish()

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


    private fun hidePrices(originalAmount: Double): Double {
        if (confirmPage?.getCheckedStatus() == true) {
            return 0.0
        }
        return originalAmount
    }

    private fun pullDataAndDisplay() {
        confirmPage?.getData()
        if (confirmPage?._dataFromSurvey != null) {
            confirmPage?.updateTotal()
        } else
            Toast.makeText(requireContext(), "Error Occurred!!.", Toast.LENGTH_LONG).show()
    }
}





