package com.example.surveyapp.database

import android.graphics.pdf.PdfDocument

data class PDF(
    var pdfID: Int,
    //TODO add Foreign Key
    var surveyID: Int,
    var file: PdfDocument,
)
