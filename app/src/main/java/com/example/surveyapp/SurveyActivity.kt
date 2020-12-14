package com.example.surveyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.surveyapp.fragments.Tabs.adapters.createTab.CreateSurveyFragment
import com.example.surveyapp.fragments.Tabs.adapters.checkListTab.CreateSurveyP1p5Fragment
import com.example.surveyapp.fragments.Tabs.adapters.PreviousWorkAndLockTab.CreateSurveyP2Fragment
import com.example.surveyapp.fragments.Tabs.adapters.SoRTab.SOR_Fragment
import com.example.surveyapp.fragments.Tabs.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class SurveyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

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