package com.example.surveyapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.example.surveyapp.R
import com.example.surveyapp.application.SurveyApplication
import com.example.surveyapp.fragments.Tabs.adapters.createTab.CreateSurveyFragment
import com.example.surveyapp.fragments.Tabs.adapters.checkListTab.CreateSurveyP1p5Fragment
import com.example.surveyapp.fragments.Tabs.adapters.PreviousWorkAndLockTab.CreateSurveyP2Fragment
import com.example.surveyapp.fragments.Tabs.adapters.SoRTab.SOR_Fragment
import com.example.surveyapp.fragments.Tabs.adapters.SoRTab.SurveySorViewModel
import com.example.surveyapp.fragments.Tabs.adapters.SoRTab.SurveySorViewModelFactory
import com.example.surveyapp.fragments.Tabs.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class SurveyActivity : AppCompatActivity() {

    val sorViewModel: SurveySorViewModel by viewModels {
        SurveySorViewModelFactory((application as SurveyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        sorViewModel.get("dsadsa")
        setUpTabs()
    }


    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(CreateSurveyFragment(), "Survey Details")
        adapter.addFragment(CreateSurveyP1p5Fragment(), "Checklist")
        adapter.addFragment(CreateSurveyP2Fragment(), "Previous Work")
        adapter.addFragment(SOR_Fragment(), "Add SOR")
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter
        val tabs = findViewById<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


    }
}