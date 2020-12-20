package com.example.surveyapp.fragments.sorTab

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.surveyapp.databinding.FragmentSORBinding
import com.example.surveyapp.R


/**
 * A simple [Fragment] subclass.
 * Use the [SOR_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SOR_Fragment : Fragment() {
    //to make the code cleaner create a viewModel in the fragment tab.
    private lateinit var viewModel: SurveySorViewModel


    //Instantiate binding variable
    // it has to be var and it must be a lateinit
    //private lateinit var binding : FragmentSORBinding

    @SuppressLint("WrongThread")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Instantiate the binding
        val binding: FragmentSORBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_s_o_r_, container, false)
//
//
//
//        val binding: FragmentMainMenuBinding = DataBindingUtil.inflate(
//            inflater,
//            R.layout.fragment_main_menu,
//            container,
//            false
//        )

        // initialize the viewModel to be the bind object with the viewModel in activity


        // att


        // Inflate the layout for this fragment
        // return binding root
        return binding.root
    }







}