package com.example.surveyapp.fragments.PreviousWorkAndLockTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.databinding.FragmentCreateSurveyP2Binding
import com.example.surveyapp.domains.SurveySORs


class CreateSurveyP2Fragment : Fragment() {


    private lateinit var binding: FragmentCreateSurveyP2Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_survey_p2, container, false)


        registerTextChange(1, binding.question1, binding.rechargeQ1, binding.comment1)
        registerTextChange(5, binding.question5, binding.rechargeQ5, binding.comment5)
        registerTextChange(6, binding.question6, binding.rechargeQ6, binding.comment6)
        registerTextChange(7, binding.question7, binding.rechargeQ7, binding.comment7)
        registerTextChange(8, binding.question8, binding.rechargeQ8, binding.comment8)
        registerTextChange(9, binding.question9, binding.rechargeQ9, binding.comment9)
        registerTextChange(10, binding.question10, binding.rechargeQ10, binding.comment10)
        registerTextChange(11, binding.question11, binding.rechargeQ11, binding.comment11)
        registerTextChange(12, binding.question12, binding.rechargeQ12, binding.comment12)
        registerTextChange(13, binding.question13, binding.rechargeQ13, binding.comment13)
        registerTextChange(14, binding.question14, binding.rechargeQ14, binding.comment14)
        registerTextChange(15, binding.question15, binding.rechargeQ15, binding.comment15)
        registerTextChange(16, binding.question16, binding.rechargeQ16, binding.comment16)
        registerTextChange(17, binding.question17, binding.rechargeQ17, binding.comment17)

        loadAnswersToQuestion(1, binding.question1, binding.rechargeQ1, binding.comment1)
        loadAnswersToQuestion(5, binding.question5, binding.rechargeQ5, binding.comment5)
        loadAnswersToQuestion(6, binding.question6, binding.rechargeQ6, binding.comment6)
        loadAnswersToQuestion(7, binding.question7, binding.rechargeQ7, binding.comment7)
        loadAnswersToQuestion(8, binding.question8, binding.rechargeQ8, binding.comment8)
        loadAnswersToQuestion(9, binding.question9, binding.rechargeQ9, binding.comment9)
        loadAnswersToQuestion(10, binding.question10, binding.rechargeQ10, binding.comment10)
        loadAnswersToQuestion(11, binding.question11, binding.rechargeQ11, binding.comment11)
        loadAnswersToQuestion(12, binding.question12, binding.rechargeQ12, binding.comment12)
        loadAnswersToQuestion(13, binding.question13, binding.rechargeQ13, binding.comment13)
        loadAnswersToQuestion(14, binding.question14, binding.rechargeQ14, binding.comment14)
        loadAnswersToQuestion(15, binding.question15, binding.rechargeQ15, binding.comment15)
        loadAnswersToQuestion(16, binding.question16, binding.rechargeQ16, binding.comment16)
        loadAnswersToQuestion(17, binding.question17, binding.rechargeQ17, binding.comment17)

      /*
        PASSED
         */
        /// Box listener
        registerTrueOrFalseClick(2, binding.question2Box, binding.rechargeQ2, binding.comment2)
        registerTrueOrFalseClick(3, binding.question3Box, binding.rechargeQ3, binding.comment3)
        registerTrueOrFalseClick(4, binding.question4Box, binding.rechargeQ4, binding.comment4)


        /*
      PASSED
       */
        registerClickRecharge(2, binding.rechargeQ2, binding.question2Box, binding.comment2)
        registerClickRecharge(3, binding.rechargeQ3, binding.question3Box, binding.comment3)
        registerClickRecharge(4, binding.rechargeQ4, binding.question4Box, binding.comment4)


        //
        registerRechargeClick(1, binding.rechargeQ1, binding.question1, binding.comment1)
        registerRechargeClick(5, binding.rechargeQ5, binding.question5, binding.comment5)
        registerRechargeClick(6, binding.rechargeQ6, binding.question6, binding.comment6)
        registerRechargeClick(7, binding.rechargeQ7, binding.question7, binding.comment7)
        registerRechargeClick(8, binding.rechargeQ8, binding.question8, binding.comment8)
        registerRechargeClick(9, binding.rechargeQ9, binding.question9, binding.comment9)
        registerRechargeClick(10, binding.rechargeQ10, binding.question10, binding.comment10)
        registerRechargeClick(11, binding.rechargeQ11, binding.question11, binding.comment11)
        registerRechargeClick(12, binding.rechargeQ12, binding.question12, binding.comment12)
        registerRechargeClick(13, binding.rechargeQ13, binding.question13, binding.comment13)
        registerRechargeClick(14, binding.rechargeQ14, binding.question14, binding.comment14)
        registerRechargeClick(15, binding.rechargeQ15, binding.question15, binding.comment15)
        registerRechargeClick(16, binding.rechargeQ16, binding.question16, binding.comment16)
        registerRechargeClick(17, binding.rechargeQ17, binding.question17, binding.comment17)


        //Text Change

        registerCommentChange(1, binding.rechargeQ1, binding.question1, binding.comment1)
        registerCommentChange(5, binding.rechargeQ5, binding.question5, binding.comment5)
        registerCommentChange(6, binding.rechargeQ6, binding.question6, binding.comment6)
        registerCommentChange(7, binding.rechargeQ7, binding.question7, binding.comment7)
        registerCommentChange(8, binding.rechargeQ8, binding.question8, binding.comment8)
        registerCommentChange(9, binding.rechargeQ9, binding.question9, binding.comment9)
        registerCommentChange(10, binding.rechargeQ10, binding.question10, binding.comment10)
        registerCommentChange(11, binding.rechargeQ11, binding.question11, binding.comment11)
        registerCommentChange(12, binding.rechargeQ12, binding.question12, binding.comment12)
        registerCommentChange(13, binding.rechargeQ13, binding.question13, binding.comment13)
        registerCommentChange(14, binding.rechargeQ14, binding.question14, binding.comment14)
        registerCommentChange(15, binding.rechargeQ15, binding.question15, binding.comment15)
        registerCommentChange(16, binding.rechargeQ16, binding.question16, binding.comment16)
        registerCommentChange(17, binding.rechargeQ17, binding.question17, binding.comment17)



        registerCCommentChangeBox(2, binding.rechargeQ2, binding.question2Box, binding.comment2)
        registerCCommentChangeBox(3, binding.rechargeQ3, binding.question3Box, binding.comment3)
        registerCCommentChangeBox(4, binding.rechargeQ4, binding.question4Box, binding.comment4)



        SurveyActivity.prevViewModel?.changeDetect?.observe(viewLifecycleOwner, Observer { flag ->
            if (flag == true) {
                val guiInterface = SurveyActivity.prevViewModel?.returnPreviosWorkData()
               if(guiInterface != null) {
                   sortDataLoadedList(guiInterface)
               }
            }
        })


        return binding.root
    }

    private fun sortDataLoadedList(guiInterface: List<SurveySORs>?) {

        addToGui2(guiInterface!!.get(0), binding.rechargeQ1, binding.question1, binding.comment1)

        //CALLS A DIFFERENT FUNCTION BECAUSE THE FORMAT IS DIFFERNT
        addToGui2B(guiInterface!!.get(1), binding.rechargeQ2, binding.question2Box, binding.comment2)
        addToGui2B(guiInterface!!.get(2), binding.rechargeQ3, binding.question3Box, binding.comment3)
        addToGui2B(guiInterface!!.get(3), binding.rechargeQ4, binding.question4Box, binding.comment4)


        addToGui2(guiInterface!!.get(4), binding.rechargeQ5, binding.question5, binding.comment5)
        addToGui2(guiInterface!!.get(5), binding.rechargeQ6, binding.question6, binding.comment6)
        addToGui2(guiInterface!!.get(6), binding.rechargeQ7, binding.question7, binding.comment7)
        addToGui2(guiInterface!!.get(7), binding.rechargeQ8, binding.question8, binding.comment8)
        addToGui2(guiInterface!!.get(8), binding.rechargeQ9, binding.question9, binding.comment9)
        addToGui2(guiInterface!!.get(9), binding.rechargeQ10, binding.question10, binding.comment10)
        addToGui2(guiInterface!!.get(10), binding.rechargeQ11, binding.question11, binding.comment11)
        addToGui2(guiInterface!!.get(11), binding.rechargeQ12, binding.question12, binding.comment12)
        addToGui2(guiInterface!!.get(12), binding.rechargeQ13, binding.question13, binding.comment13)
        addToGui2(guiInterface!!.get(13), binding.rechargeQ14, binding.question14, binding.comment14)
        addToGui2(guiInterface!!.get(14), binding.rechargeQ15, binding.question15, binding.comment15)
        addToGui2(guiInterface!!.get(15), binding.rechargeQ16, binding.question16, binding.comment16)
        addToGui2(guiInterface!!.get(16), binding.rechargeQ17, binding.question17, binding.comment17)


    }

    private fun addToGui2(survey: SurveySORs, rechargeBox: CheckBox, question: EditText, comment: EditText) {
        question.setText(survey.quantity.toString())
        rechargeBox.setChecked(survey.isRecharge)
        rechargeBox.jumpDrawablesToCurrentState()
        comment.setText(survey.surveyorDescription)
    }

    private fun addToGui2B(survey: SurveySORs, rechargeBox: CheckBox, question: CheckBox, comment: EditText) {
        question.setChecked(returnNumberAsBoolean(survey.quantity))
        question.jumpDrawablesToCurrentState()
        rechargeBox.setChecked(survey.isRecharge)
        rechargeBox.jumpDrawablesToCurrentState()
        binding.comment2.setText(survey.surveyorDescription)
    }



    private fun returnNumberAsBoolean(num:Double):Boolean{
        return num == 1.0
    }


    private fun registerCCommentChangeBox(
        id: Int,
        isRecharge: CheckBox,
        questionBox: CheckBox,
        comment: EditText
    ) {
        comment.addTextChangedListener {
            var com = comment.text.toString()
            var isRecharged = isRecharge.isChecked
            var qty: Double

            if (questionBox.isChecked) {
                qty = 1.0
            } else
                qty = 0.0

            SurveyActivity.prevViewModel!!.addChangetoCheckedVariables(id, qty, isRecharged, com)

        }
    }

    private fun registerCommentChange(
        id: Int,
        recharge: CheckBox,
        qty: EditText,
        comment: EditText
    ) {
        comment.addTextChangedListener {
            val comments = comment.text.toString()
            val isRecharge = recharge.isChecked
            var quantity = qty.text.toString()
            if (quantity == null || quantity == "") {
                quantity = "0.0"
            }
            SurveyActivity.prevViewModel!!.addChangesToVariable(
                id,
                quantity.toDouble(),
                isRecharge,
                comments
            )
        }
    }

    private fun registerClickRecharge(
        id: Int,
        recharge: CheckBox,
        questionBox: CheckBox,
        userComment: EditText
    ) {
        var quantity: Double

        recharge.setOnClickListener {
            val questionHasRecharge = recharge.isChecked
            val QuestionIsTrue = questionBox.isChecked
            val commentry = userComment.text.toString()
            if (QuestionIsTrue) {
                quantity = 1.0
            } else
                quantity = 0.0

            SurveyActivity.prevViewModel!!.addChangetoCheckedVariables(
                id,
                quantity,
                questionHasRecharge,
                commentry
            )

        }

    }


    private fun registerTrueOrFalseClick(
        id: Int,
        questionBox: CheckBox,
        recharge: CheckBox,
        comment: EditText
    ) {

        questionBox.setOnClickListener {
            var quantity: Double
            //  Toast.makeText(requireContext(), " WAS CLICKED", Toast.LENGTH_SHORT).show()
            val QuestionIsTrue = questionBox.isChecked
            val questionHasRecharge = recharge.isChecked
            val commentry = comment.text.toString()

            if (QuestionIsTrue) {
                quantity = 1.0
            } else
                quantity = 0.0


            SurveyActivity.prevViewModel!!.addChangetoCheckedVariables(
                id,
                quantity,
                questionHasRecharge,
                commentry
            )
        }

    }


    /*
    PASSED
     */
    private fun registerTextChange(
        id: Int,
        questionEntry: EditText,
        rechargeBox: CheckBox,
        comment: EditText
    ) {
        questionEntry.addTextChangedListener {
            var Testnumber = questionEntry.text.toString().trim()
            val isNumberSuitable = testNumberLength(Testnumber)
            //pass the number Test

            if (isNumberSuitable) {
                if (!Testnumber.equals(".")) {

                    var number: Double = returnAnyNumberWithoutError(Testnumber)

                    val isrecharge = rechargeBox.isChecked
                    var comment = comment.text.toString()

                    SurveyActivity.prevViewModel!!.addChangesToVariable(
                        id,
                        number,
                        isrecharge,
                        comment
                    )
                } else
                    questionEntry.setText("")
            } else
                questionEntry.setText("")
        }
    }


    /*
    PASSED
     */
    private fun testNumberLength(testnumber: String): Boolean {
        if (testnumber.length > 4) {
            Toast.makeText(requireContext(), "NUMBER TOO LARGE!!", Toast.LENGTH_SHORT).show()
            return false
        } else
            return true
    }

    /*
PASSED
 */

    fun returnAnyNumberWithoutError(numberToCheck: String): Double {
        var testNumber = numberToCheck
        if (testNumber.equals("")) {
            testNumber = "0.0"
            return testNumber.toDouble()
        }
        return testNumber.toDouble()
    }



    private fun loadAnswersToQuestion(
        i: Int,
        question1: EditText,
        rechargeQ1: CheckBox,
        comment1: EditText
    ) {}


    private fun registerRechargeClick(
        id: Int,
        rechargeBox: CheckBox,
        questionEntry: EditText,
        userComment: EditText
    ) {
        rechargeBox.setOnClickListener {

            // get value of the checked box,
            // get the value of the number field
            val isRecharge = rechargeBox.isChecked
            val number = returnAnyNumberWithoutError(questionEntry.text.toString())
            val comment = userComment.text.toString()

            //pass to the function
            SurveyActivity.prevViewModel!!.addChangesToVariable(id, number, isRecharge, comment)


        }
    }


}