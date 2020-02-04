package com.divyanshu.studentadmin.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.divyanshu.studentadmin.R
import com.divyanshu.studentadmin.databinding.FragmentAddStudentsBinding

class AddStudentsFragment : Fragment() {

    var fragmentAddStudentsBinding: FragmentAddStudentsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAddStudentsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_add_students,
            container,
            false
        )
        return fragmentAddStudentsBinding!!.root
    }
}
