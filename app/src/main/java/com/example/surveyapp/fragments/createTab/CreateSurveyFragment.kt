package com.example.surveyapp.fragments.createTab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.activities.SurveyActivity.Companion.createSurveyPage
import com.example.surveyapp.databinding.FragmentCreateSurveyBinding
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match

class CreateSurveyFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var binding: FragmentCreateSurveyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_survey, container, false)
        // Inflate the layout for this fragment

        SurveyActivity.createSurveyPage?.surveyName_?.observe(viewLifecycleOwner, Observer { name->
            binding.nameEdit.setText(name)

        })
        SurveyActivity.createSurveyPage?.address_?.observe(viewLifecycleOwner, Observer { address->
            binding.addressEdit.setText(address)

        })
        SurveyActivity.createSurveyPage?.postCode_?.observe(viewLifecycleOwner, Observer { postCode->
            binding.postEdit.setText(postCode)

        })
        SurveyActivity.createSurveyPage?.phoneNumber_?.observe(viewLifecycleOwner, Observer { phoneNumber->
            binding.phoneNumEdit.setText(phoneNumber)

        })
        SurveyActivity.createSurveyPage?.day_?.observe(viewLifecycleOwner, Observer { day->
            var month = createSurveyPage?.getMonth()
            var year = createSurveyPage?.getYear()

            val dateFormat = SimpleDateFormat("dd-M-yyyy")
            val x = "${day}-${month}-${year}"
            val date = dateFormat.parse(x)




            binding.calendarView.setDate(date.getTime())
            binding.dateEdit.setText("${day}/${month}/${year}")

        })

        SurveyActivity.createSurveyPage?.surveyType_?.observe(viewLifecycleOwner, Observer { surveyType->
            if(surveyType == constant.CAPTIAL){
                binding.capitalRB.setChecked(true)
            }
             if(surveyType == constant.REVENUE) {
                 binding.revenueRB.setChecked(true)}

        })



        binding.capitalRB.setOnClickListener {
            SurveyActivity.createSurveyPage?.setSurveryType(constant.CAPTIAL)
        }

        binding.revenueRB.setOnClickListener {
            SurveyActivity.createSurveyPage?.setSurveryType(constant.REVENUE)
        }



        binding.nameEdit.addTextChangedListener { text ->
            SurveyActivity.createSurveyPage?.storeName(text.toString())
        }

        binding.addressEdit.addTextChangedListener { text ->
            SurveyActivity.createSurveyPage?.storeAddress(text.toString())
        }

        binding.postEdit.addTextChangedListener { text ->
            SurveyActivity.createSurveyPage?.storePostCode(text.toString())
        }

        binding.phoneNumEdit.addTextChangedListener { text ->
            SurveyActivity.createSurveyPage?.storePhoneNumber(text.toString())
        }



//        binding.dateEdit.addTextChangedListener { text ->
//            SurveyActivity.createSurveyPage?.storeDate(text.toString())
//        }


        binding.calendarView.setOnDateChangeListener{CalendarView, year, month, dayOfMonth ->

            createSurveyPage?.month_?.value = month+1
            createSurveyPage?.year_?.value = year
            createSurveyPage?.day_?.value = dayOfMonth

        }






        return binding.root
    }

}