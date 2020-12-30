package com.example.surveyapp.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.surveyapp.R
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class PDF : AppCompatActivity() {

    private val STORAGE_CODE: Int = 100
    lateinit var text: String
    lateinit var textField: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p_d_f)

        var pkgn = intent.extras?.getString("surveyInfo")
        var hello = intent.extras?.getString("test")
        textField = findViewById<EditText>(R.id.textField2)
        var createPdf = findViewById<Button>(R.id.createButton)

        // Toast.makeText(this, pkgn.toString(), Toast.LENGTH_SHORT).show()
        textField.setText(pkgn)


        registerPdfButton(createPdf)


    }

    private fun registerPdfButton(saveBtn: Button?) {

        saveBtn!!.setOnClickListener { it ->

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                //System OS >=
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, STORAGE_CODE)
                } else {
                    /// permission alreadgy granted, call save PDF method
                    savePdf()

                }


            } else {
                savePdf()
            }

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
                    Toast.makeText(this, "Permission denied .....!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun savePdf() {
        // Create object of Document class
        val mDoc = Document()
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
            mDoc.addAuthor("Samuel Brandenburg")

            // you add more freatures like this.
            mDoc.add(Paragraph(textField.text.toString()))
            mDoc.add(Paragraph("Hello     I Don't know"))
            mDoc.close()

            Toast.makeText(this, "$mFileName.pdf\nis saved to\n$mFilePath", Toast.LENGTH_SHORT)
                .show()
        } catch (e: Exception) {
            //
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }


}