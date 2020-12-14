package com.example.surveyapp.fragments.Tabs.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

@Suppress("DEPRECATION")
class ViewPagerAdapter(supportFragmentManager: FragmentManager) :
    FragmentPagerAdapter(supportFragmentManager) {
    private val mFragmentList = ArrayList<Fragment>()
    private val mFramgentTitleList = ArrayList<String>()


    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return mFramgentTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFramgentTitleList.add(title)
    }
}