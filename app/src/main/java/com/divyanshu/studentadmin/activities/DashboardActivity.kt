package com.divyanshu.studentadmin.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.divyanshu.studentadmin.R
import com.divyanshu.studentadmin.databinding.ActivityDashboardBinding
import com.divyanshu.studentadmin.fragments.AddStudentsFragment
import com.divyanshu.studentadmin.fragments.StudentsListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {
    var toolbar: ActionBar? = null
    var activityDashboardBinding: ActivityDashboardBinding? = null
    var fragment: Fragment? = null
    var bottomNavigation: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        toolbar = supportActionBar
        bottomNavigation = findViewById(R.id.bottom_navigation)

        fragment = AddStudentsFragment()
        replaceFragment(fragment as AddStudentsFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        bottomNavigation!!.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.addStudent -> {
                    fragment = AddStudentsFragment()
                    replaceFragment(fragment as AddStudentsFragment)
                }
                R.id.viewStudent -> {
                    fragment = StudentsListFragment()
                    replaceFragment(fragment as StudentsListFragment)
                }

            }
            false
        }

    //// while having this much data
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.dashframeLayout, fragment)
            .addToBackStack(null).commit()
    }


}
