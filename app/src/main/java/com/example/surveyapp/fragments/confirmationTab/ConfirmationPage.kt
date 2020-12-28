package com.example.surveyapp.fragments.confirmationTab

import android.os.Bundle
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
import java.text.DecimalFormat


/**
 * A simple [Fragment] subclass.
 * Use the [confirmationPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfirmationPage : Fragment() {
    private val currency = DecimalFormat("£###,###.##")
    private val numberCounter = DecimalFormat("####. ")
    lateinit var binding: FragmentConfirmationPageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_confirmation_page, container, false)
        binding.textField.isFocusable = false

        buttonListener(binding.button2)










        return binding.root


    }

    private fun buttonListener(button2: Button) {

        button2.setOnClickListener { it ->
            var dataFromSor = SurveyActivity.sorViewModel!!.returnListSORLIST()
            var dataFromPrev = SurveyActivity.prevViewModel!!.returnPreviosWorkData()
            var experimentunite = combineList(dataFromSor, dataFromPrev)

            Toast.makeText(requireContext(), experimentunite.size.toString(), Toast.LENGTH_SHORT)
                .show()
            binding.textField.text.clear()
            var total: Double = 0.0
            var header = "\t\t\tSoR\t\t\t\t\t\t\t\tQTY\t\t\t\t\t\tTotal\n"
            var dataString: String = ""
            var count = 1
            for (sor in experimentunite) {
                dataString += (numberCounter.format(count) + sor.sorCode + "\t\t\t\t\t\t" + sor.quantity.toString() + "\t\t\t\t\t\t" + currency.format(

                    sor.total
                ) + "\n" + sor.surveyorDescription)
                count += 1
                total += sor.total
//            }

                binding.textField.setText(header + dataString)
                binding.total.text = currency.format(total)

            }


        }

    }


}


fun combineList(dataFromSor: List<SurveySORs>, dataFromPrev: List<SurveySORs>): List<SurveySORs> {
    var list = mutableListOf<SurveySORs>()


    for (data in dataFromSor) {
        list.add(data)
    }
    for (data in dataFromPrev) {
        list.add(data)
    }


    return list
}