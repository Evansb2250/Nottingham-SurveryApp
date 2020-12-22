package com.example.surveyapp.fragments.mainFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.WorkerThread
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.databinding.FragmentMainMenuBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [MainMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters



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


        binding.createSurveyButtonMain.setOnClickListener({
            changeToSurveyActivity()
        })


        binding.goToViewSurveyButton.setOnClickListener {
            changeToViewSurveyFragment(it)
        }

        return binding.root
    }

    private fun changeToSurveyActivity() {
        //TODO comment out lines to test SOR DATABASE

        val intent = Intent(activity, SurveyActivity::class.java)
        requireActivity().startActivity(intent)

        //requireActivity().finish()
    }

    private fun changeToViewSurveyFragment(it: View) {
        it.findNavController()
            .navigate(MainMenuFragmentDirections.actionMainMenuFragmentToViewSurveyFragment())
    }


}