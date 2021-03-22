package com.example.surveyapp.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.R
import com.example.surveyapp.application.SurveyApplication
import com.example.surveyapp.fragments.asbestoRomval.AsbestoRemoval
import com.example.surveyapp.fragments.PreviousWorkAndLockTab.CreateSurveyP2Fragment
import com.example.surveyapp.fragments.PreviousWorkAndLockTab.previosWorkViewModel
import com.example.surveyapp.fragments.PreviousWorkAndLockTab.previosWorkViewModelFactory
import com.example.surveyapp.fragments.asbestoRomval.AbestoViewModel
import com.example.surveyapp.fragments.asbestoRomval.AbestoViewModelFactory
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

        var isApplicationInEditMode = false
        var createSurveyPage: createSurveyViewModel? = null
        var sorViewModel: SurveySorViewModel? = null
        var prevViewModel: previosWorkViewModel? = null
        var checkListVM: ChecklistViewModel? = null
        var confirmPage: ConfirmViewModel? = null
        var abesto: AbestoViewModel?= null
        var SurveyID:Int?= null
    }

    private val adapter = ViewPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        val bundle = getIntent().getExtras()
        val surveyActivityModel : SurveyActivityViewModel by viewModels {SurveyActivityViewModelFactory((application as SurveyApplication).repository)}
        val isThisSurveyBeingEdited = checkBundleForEditRequest(bundle, surveyActivityModel)


        surveyActivityModel.id.observe(this, Observer { id ->

            Log.i("CHECKs", id.toString())
            preloadAllViewModelsWithExistingData(isThisSurveyBeingEdited, surveyActivityModel)
            updateViewModelsSurveyID(id)

        })

        // viewModel  = ViewModelProvider(this).get(SurveySorViewModel::class.java)
        initializeViewModels()
        setUpTabs()
    }


    private fun updateViewModelsSurveyID(id: Int) {
        confirmPage?.surveyIDTextView?.value = id
        SurveyID = id
        checkListVM?.setSurveyID(id)
        prevViewModel?.setSurveyID(id)
    }


    private fun preloadAllViewModelsWithExistingData(
        isThisSurveyBeingEdited: Boolean,
        surveyActivityModel: SurveyActivityViewModel
    ) {
        if(isThisSurveyBeingEdited){
            //Next time an insert is called the prexisting data will be removed first
            isApplicationInEditMode = true
            //Allocates prexisting data to each tabs viewmodel (functions will repopulate data in GUI)
            createSurveyPage?.addSurveyPackage(surveyActivityModel.restoreSurveyHelperClass.returnCreatePagePackage())
            abesto?.restoreOldText(surveyActivityModel.restoreSurveyHelperClass.getAbesto())
            sorViewModel?.loadPreviousSorList(surveyActivityModel.restoreSurveyHelperClass.getSorsBelongingToSorFragament())
            checkListVM?.requestCheckList( surveyActivityModel.restoreSurveyHelperClass.getCheckList())
            prevViewModel?.loadOldSors(surveyActivityModel.restoreSurveyHelperClass.getListOfPreviousSors())

        }
    }

    private fun checkBundleForEditRequest(bundle: Bundle?, surveyActivityModel: SurveyActivityViewModel) :Boolean {

        if(bundle != null){
            val message = bundle?.getString(constant.bundleMessage)
            if(message != null && message == constant.editSurveyTag){
                val id = bundle?.getString(constant.idTag)
                surveyActivityModel.createMessage(constant.getExistingSurvey, id!!.toInt())
                return true
            }

        }else
            surveyActivityModel.createMessage(constant.getLastSurvey, -1)
            return false
    }



    private fun initializeViewModels() {
        val viewModel: SurveySorViewModel by viewModels { SurveySorViewModelFactory((application as SurveyApplication).repository) }
        val prevSoRViewModel: previosWorkViewModel by viewModels { previosWorkViewModelFactory((application as SurveyApplication).repository) }
        val checklistViewModel: ChecklistViewModel by viewModels { ChecklistViewModelFactory((application as SurveyApplication).repository) }
        val confirmationVM: ConfirmViewModel by viewModels { confirmViewModelFactory((application as SurveyApplication).repository) }
        val createSVM: createSurveyViewModel by viewModels { createSurveyViewModelFactory((application as SurveyApplication).repository) }
        val abestoViewModel: AbestoViewModel by viewModels { AbestoViewModelFactory((application as SurveyApplication).repository) }

        // Short CUT TO CLEAR A BUG
        //   sorViewModel?.addedSorList?.clear()
        abesto = abestoViewModel
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
        adapter.addFragment(CreateSurveyFragment(), "Survey Details")
        adapter.addFragment(CreateSurveyP1p5Fragment(), "Checklist")
        adapter.addFragment(AsbestoRemoval(), "Asbesto Removal")
        adapter.addFragment(CreateSurveyP2Fragment(), "Previous Work")
        adapter.addFragment(SOR_Fragment(), "Add SOR")
        adapter.addFragment(ConfirmationPage(), "save / cancel")
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        //
        viewPager.setOffscreenPageLimit(6)
        viewPager.adapter = adapter
        val tabs = findViewById<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


    }

}











