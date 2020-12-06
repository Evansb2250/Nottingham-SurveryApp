package com.example.surveyapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.surveyapp.R


/**
 * A simple [Fragment] subclass.
 * Use the [SOR_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SOR_Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_s_o_r_, container, false)
    }


}