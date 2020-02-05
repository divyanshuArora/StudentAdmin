package com.divyanshu.studentadmin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.divyanshu.studentadmin.R
import com.divyanshu.studentadmin.databinding.ActivityDashboardBinding
import com.divyanshu.studentadmin.fragments.AddStudentsFragment
import com.divyanshu.studentadmin.fragments.StudentsListFragment
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.ArrayList

class DashboardActivity : AppCompatActivity() {
    var activityDashboardBinding: ActivityDashboardBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        setupViewPager(pager)
        tab_layout.setupWithViewPager(pager)
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFrag(
            AddStudentsFragment(ContextCompat.getColor(this, R.color.white)), "Add Students"
        )
        adapter.addFrag(
            StudentsListFragment(ContextCompat.getColor(this, R.color.white)), "View Students"
        )

        viewPager.adapter = adapter
        viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int)
            {

            }

            override fun onPageSelected(position: Int) {
                adapter.notifyDataSetChanged()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }
}
