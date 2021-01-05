package com.example.surveyapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.surveyapp.R
import com.example.surveyapp.application.SurveyApplication
import com.example.surveyapp.fragments.PreviousWorkAndLockTab.CreateSurveyP2Fragment
import com.example.surveyapp.fragments.PreviousWorkAndLockTab.previosWorkViewModel
import com.example.surveyapp.fragments.PreviousWorkAndLockTab.previosWorkViewModelFactory
import com.example.surveyapp.fragments.checkListTab.ChecklistViewModel
import com.example.surveyapp.fragments.checkListTab.ChecklistViewModelFactory
import com.example.surveyapp.fragments.checkListTab.CreateSurveyP1p5Fragment
import com.example.surveyapp.fragments.confirmationTab.ConfirmViewModel
import com.example.surveyapp.fragments.confirmationTab.ConfirmationPage
import com.example.surveyapp.fragments.confirmationTab.confirmViewModelFactory
import com.example.surveyapp.fragments.createTab.CreateSurveyFragment
import com.example.surveyapp.fragments.createTab.createSurveyViewModel
import com.example.surveyapp.fragments.createTab.createSurveyViewModelFactory
import com.example.surveyapp.fragments.sorTab.SOR_Fragment
import com.example.surveyapp.fragments.sorTab.SurveySorViewModel
import com.example.surveyapp.fragments.sorTab.SurveySorViewModelFactory
import com.example.surveyapp.fragments.tabAdapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class SurveyActivity : AppCompatActivity() {

    companion object {
        // global sor viewModel
        var createSurveyPage: createSurveyViewModel? = null
        var sorViewModel: SurveySorViewModel? = null
        var prevViewModel: previosWorkViewModel? = null
        var checkListVM: ChecklistViewModel? = null
        var confirmPage: ConfirmViewModel? = null


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        // viewModel  = ViewModelProvider(this).get(SurveySorViewModel::class.java)


        initializeViewModels()
        setUpTabs()
    }


    private fun initializeViewModels() {
        val viewModel: SurveySorViewModel by viewModels { SurveySorViewModelFactory((application as SurveyApplication).repository) }
        val prevSoRViewModel: previosWorkViewModel by viewModels { previosWorkViewModelFactory((application as SurveyApplication).repository) }
        val checklistViewModel: ChecklistViewModel by viewModels { ChecklistViewModelFactory((application as SurveyApplication).repository) }
        val confirmationVM: ConfirmViewModel by viewModels { confirmViewModelFactory((application as SurveyApplication).repository) }
        val createSVM: createSurveyViewModel by viewModels { createSurveyViewModelFactory((application as SurveyApplication).repository) }
        // Short CUT TO CLEAR A BUG
        //   sorViewModel?.addedSorList?.clear()

        createSurveyPage = createSVM
        sorViewModel = viewModel
        checkListVM = checklistViewModel
        prevViewModel = prevSoRViewModel
        confirmPage = confirmationVM

    }

    override fun onBackPressed() {
        Toast.makeText(this, "Go To cancel button to exit Survey", Toast.LENGTH_LONG).show()
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(CreateSurveyFragment(), "Survey Details")
        adapter.addFragment(CreateSurveyP1p5Fragment(), "Checklist")
        adapter.addFragment(CreateSurveyP2Fragment(), "Previous Work")
        adapter.addFragment(SOR_Fragment(), "Add SOR")
        adapter.addFragment(ConfirmationPage(), "save / cancel")
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter
        val tabs = findViewById<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


    }

}