package com.example.surveyapp.fragments.confirmationTab

import android.app.usage.UsageEvents.Event.NONE
import android.os.Environment
import android.util.Log
import com.example.surveyapp.CONSTANTS.PDFconstants
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.CONSTANTS.constant.Companion.HEADERS1
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.activities.SurveyActivity.Companion.confirmPage
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class PdfCreator(private val viewModel: ConfirmViewModel) {
    companion object {
        var message = ""
    }

    private val currency = DecimalFormat("£###,###.##")

    suspend fun savePdf(): Boolean {
        // Create object of Document class
        val mDoc = Document(PageSize.A3.rotate())

        val mFilePath = createDocFilePath("_Survey_id_", SurveyActivity.SurveyID)

        try {
            //create instance of pdfwriter class
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

            //open the document for writing
            mDoc.open()


            //val pointColumnWidths = FloatArray(150)
            val table = PdfPTable(11)

            /*
            1.Revenue
            2.response
            3.Capital
            4.response
            5.Fire / services referral
            6.reach
            7.Rechargeable Total
            8.amount             */




            for (id in 0..10) {
                val phrase = Phrase()

                if (id == 1 || id == 4 || id == 10) {

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

                    if (id == 10) {

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






            for (id in 0..10) {
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

                if (id == 10) {
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


            for (id in 0..10) {

                val phrase = Phrase()

                if (id == 10) {
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
            cell.colspan = 11
            table.addCell(cell)


            var count = 0
            val list = confirmPage?.getSORS()
            if (list != null) {
                var cell: PdfPCell

                for (sor in list) {
                    //DIVIDER

                    cell = PdfPCell(Phrase(""))
                    cell.border = NONE
                    cell.colspan = 11
                    cell.minimumHeight = 20F
                    table.addCell(cell)


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
                    cell.colspan = 2
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



                    cell = PdfPCell(Phrase(currency.format(hidePrices(sor.total * sor.quantity))))
                    cell.border = NONE
                    table.addCell(cell)

                    cell = PdfPCell(Phrase(sor.surveyorDescription))
                    cell.colspan = 2
                    cell.border = NONE
                    table.addCell(cell)



                    cell = PdfPCell(Phrase(""))
                    cell.border = NONE
                    table.addCell(cell)

                    count += 1

                    if (count % 5 == 0) {
                        cell = PdfPCell(Phrase(""))
                        cell.border = NONE
                        cell.colspan = 11
                        cell.minimumHeight = 40F
                        table.addCell(cell)
                    }
                }
            }

            mDoc.add(table)



            saveQuestionaire2Pdf()

            mDoc.close()

            //TODO add return true for saved successfully
            return true
        } catch (e: Exception) {
            //
            Log.i("PDFERROR", e.message.toString())
            message = e.message.toString()
            //TODO add return false for failed return
            return false

//            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    suspend fun saveQuestionaire2Pdf() {

        // Create object of Document class
        val mDoc = Document(PageSize.A4)

        val mFilePath = createDocFilePath("_Questionaire_id_", SurveyActivity.SurveyID)

//            Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
        try {
            //create instance of pdfwriter class
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

            //open the document for writing
            mDoc.open()

            val table = PdfPTable(2)


            table.addCell(createBordlessTitleCell())

            table.addCell(createBlankSpace())
            table.addCell(createBlankSpace())

            table.addCell(createBordlessHeaderCell(PDFconstants.VPM))
            table.addCell(createBordlessHeaderCell(SurveyActivity.createSurveyPage?.surveyName))

            table.addCell(createBlankSpace())
            table.addCell(createBlankSpace())

            table.addCell(createBordlessHeaderCell(PDFconstants.ADDRESS))
            table.addCell(createBordlessHeaderCell(SurveyActivity.createSurveyPage?.address))

            table.addCell(createBlankSpace())
            table.addCell(createBlankSpace())


            table.addCell(createBordlessHeaderCell(PDFconstants.TAPSLOCATION))
            table.addCell(createBordlessCell(SurveyActivity.checkListVM?.getTapsLocation()))


            table.addCell(createBlankSpace())

            table.addCell(createBordlessHeaderCell(PDFconstants.DECORATIONPOINTS))
            table.addCell(createBordlessCell(SurveyActivity.checkListVM?.getDecorPoints()))

            table.addCell(createBlankSpace())


            table.addCell(createBordlessHeaderCell(PDFconstants.FLOORLEVEL))
            table.addCell(createBordlessCell(SurveyActivity.checkListVM?.getFloorLevel()))

            table.addCell(createBlankSpace())


            table.addCell(createBordlessHeaderCell(PDFconstants.ASBESTOS_REMOVAL))
            table.addCell(createBordlessCell(SurveyActivity.abesto?.getAbesto()))

            table.addCell(createBlankSpace())

            table.addCell(createBordlessHeaderCell(PDFconstants.METER_ISSUE))
            table.addCell(createBordlessCell(SurveyActivity.checkListVM?.getMeterStatus()))

            table.addCell(createBlankSpace())

            table.addCell(createBordlessHeaderCell(PDFconstants.FIRE_DOOR))
            table.addCell(createBordlessCell(SurveyActivity.checkListVM?.getFireDoorStatus()))

            table.addCell(createBlankSpace())

            table.addCell(createBordlessHeaderCell(PDFconstants.FIRE_DOOR_COMMENT))
            table.addCell(createBordlessCell(SurveyActivity.checkListVM?.getFireDoorComments()))

            table.addCell(createBlankSpace())

            table.addCell(createBordlessHeaderCell(PDFconstants.ISOLATOR))
            table.addCell(createBordlessCell(SurveyActivity.checkListVM?.getIsolatorStatus()))

            table.addCell(createBlankSpace())

            table.addCell(createBordlessHeaderCell(PDFconstants.REWIRE))
            table.addCell(createBordlessCell(SurveyActivity.checkListVM?.getRewireStatus()))

            table.addCell(createBlankSpace())

            table.addCell(createBordlessHeaderCell(PDFconstants.HEATING))
            table.addCell(createBordlessCell(SurveyActivity.checkListVM?.getHouseHeatingStatus()))

            table.addCell(createBlankSpace())

            table.addCell(createBordlessHeaderCell(PDFconstants.FAST_TRACK))
            table.addCell(createBordlessCell(SurveyActivity.checkListVM?.getFastTrackStatus()))

            table.addCell(createBlankSpace())

            table.addCell(createBordlessHeaderCell(PDFconstants.GLASS))
            table.addCell(createBordlessCell(SurveyActivity.checkListVM?.getHouseHasGlassStatus()))

            table.addCell(createBlankSpace())

            table.addCell(createBordlessHeaderCell(PDFconstants.ALTRO))
            table.addCell(createBordlessCell(SurveyActivity.checkListVM?.getAltroStatus()))

            table.addCell(createBlankSpace())

            mDoc.add(table)
            mDoc.close()


        } catch (e: Exception) {
            //

        }

    }

    private fun createBordlessTitleCell(): PdfPCell {
        val cellDescription = "NEW VOID CHECKLIST"
        val titlePhrase = Phrase()
        titlePhrase.add(Chunk(cellDescription, FontFactory.getFont(FontFactory.HELVETICA_BOLD)))
        titlePhrase.font.size = 80.0f
        val headerCell = PdfPCell(titlePhrase)
        headerCell.colspan = 2
        headerCell.border = NONE
        headerCell.minimumHeight = 20F



        return headerCell
    }


    private fun createBordlessCell(cellDescription: String?): PdfPCell {
        var phrase = Phrase()
        phrase.add(Chunk(cellDescription, FontFactory.getFont(FontFactory.HELVETICA)))
        val cell = PdfPCell(Phrase(cellDescription))
        //  cell.border = NONE
        cell.minimumHeight = 28F
        return cell
    }

    private fun createBordlessHeaderCell(cellDescription: String?): PdfPCell {
        var phrase = Phrase()
        phrase.add(Chunk(cellDescription, FontFactory.getFont(FontFactory.HELVETICA_BOLD)))
        phrase.font.size = 15.0f
        val headerCell = PdfPCell(phrase)
        headerCell.border = NONE

        return headerCell
    }

    private fun hidePrices(originalAmount: Double): Double {
        if (SurveyActivity.confirmPage?.getCheckedStatus() == true) {
            return 0.0
        }
        return originalAmount
    }


    private fun createBlankSpace(): PdfPCell {
        val cell = PdfPCell(Phrase(""))
        cell.border = NONE
        cell.colspan = 11
        cell.minimumHeight = 15F

        return cell

    }


    private fun createDocFilePath(pdfType: String, surveyID: Int?): String {
        var mFileName = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(System.currentTimeMillis())
        // PDF file path
        val fileName = pdfType + SurveyActivity.SurveyID
        mFileName = mFileName + fileName

        return Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
    }


    private fun recharge(isRecharge: Boolean): String {
        if (isRecharge.equals(true)) {
            return "Y"
        } else return ""
    }

}