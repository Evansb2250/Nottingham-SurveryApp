package com.example.surveyapp.fragments.mainFragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.databinding.FragmentMainMenuBinding
import kotlinx.coroutines.delay
import java.lang.Thread.sleep


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [MainMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    companion object{
        var MessageFlag = false
    }
    private var flag = false
    private lateinit var progress :AlertDialog
   private lateinit var intent:Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentMainMenuBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_menu,
            container,
            false
        )
        progress = AlertDialog.Builder(requireContext()).create()


        if(flag == false){
            progress?.dismiss()
        }

        binding.createSurveyButtonMain.setOnClickListener { it ->



            if (flag != true) {
                displayLoadMessage()

                flag = true
                changeToSurveyActivity()
            }

        }



        binding.goToViewSurveyButton.setOnClickListener {
            changeToViewSurveyFragment(it)
        }


        binding.button3.setOnClickListener{
            activity?.onBackPressed()
        }
        return binding.root
    }


    private fun displayLoadMessage() {
        progress.setTitle("Loading");
        progress.setMessage("Please wait while survey builder is loading...");
        progress.setCancelable(true); // disable dismiss by tapping outside of the dialog
        progress.show();

    }

    private fun changeToSurveyActivity() {
        intent  = Intent(activity, SurveyActivity::class.java)
        requireActivity().startActivity(intent)
        //TODO comment out lines to test SOR DATABASE
        activity?.finish()

    }




    private fun changeToViewSurveyFragment(it: View) {
        it.findNavController()
            .navigate(MainMenuFragmentDirections.actionMainMenuFragmentToViewSurveyFragment())
    }


}