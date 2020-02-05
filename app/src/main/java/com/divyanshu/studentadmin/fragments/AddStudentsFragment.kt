package com.divyanshu.studentadmin.fragments

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide

import com.divyanshu.studentadmin.R
import com.divyanshu.studentadmin.databinding.FragmentAddStudentsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_students.*
import org.jetbrains.anko.toast
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddStudentsFragment(color: Int) : Fragment() {
    var fragmentAddStudentsBinding: FragmentAddStudentsBinding? = null
    var studentName = ""
    var stuentDob = ""
    var studentMotherName = ""
    var studentFatherName = ""
    var studentImage = ""
    var currentDate = ""
    var selectedDateSet:Date ?= null
    var PERMISSION_ALL = 1888
    var difference:Long ?= null
    var seconds:Long ?= null
    var permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    @RequiresApi(Build.VERSION_CODES.O)
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


        fragmentAddStudentsBinding!!.imageViewEdit.setOnClickListener {
            checkPermissionForCamera()
        }


        fragmentAddStudentsBinding!!.studentDob.setOnClickListener {
            datePick()
        }

        fragmentAddStudentsBinding!!.studentDob.onFocusChangeListener =
            object : View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if (hasFocus) {
                        datePick()
                    }
                }
            }

        fragmentAddStudentsBinding!!.register.setOnClickListener {

            registerStudent()
        }

        getCurrentDate()
        return fragmentAddStudentsBinding!!.root
    }


    private fun datePick() {

        val myCalendar = Calendar.getInstance()
        var day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd-MM-yyyy" //In which you need put here
                val dateSelected = SimpleDateFormat(myFormat, Locale.US)
                fragmentAddStudentsBinding!!.studentDob.setText(dateSelected.format(myCalendar.time))

            }
        val mDatePickerDialog = DatePickerDialog(
            context,
            date,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        myCalendar.add(Calendar.DATE, -6)
        mDatePickerDialog.show()
    }


    private fun checkPermissionForCamera() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(permissions, PERMISSION_ALL);
        } else {
            showPicDialog()
        }
    }

    private fun showPicDialog() {

        val pictureDialog = AlertDialog.Builder(context!!)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery")
        pictureDialog.setItems(
            pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> galleryIntent()
            }
        }.show()
    }


    private fun galleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "select File"), 0)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun onSelectFromGalleryResult(data: Intent?) {
        if (data != null) {

            val thumbnail = MediaStore.Images.Media.getBitmap(context!!.contentResolver, data.data)
            val stream = ByteArrayOutputStream()
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            //customerProfileImg!!.setImageBitmap(thumbnail)

            Glide.with(context!!).load(data.data)
                .into(fragmentAddStudentsBinding!!.studentProfileImg)

            val array = stream.toByteArray()
            studentImage = Base64.encodeToString(array, Base64.DEFAULT).trim()

            Log.e("CustomerProfile:", "BASE64capture" + studentImage)
        }
    }

    private fun registerStudent() {
        studentName = fragmentAddStudentsBinding!!.studentName.text.toString()
        stuentDob = fragmentAddStudentsBinding!!.studentDob.text.toString()
        studentFatherName = fragmentAddStudentsBinding!!.studentFatherName.text.toString()
        studentMotherName = fragmentAddStudentsBinding!!.studentMotherName.text.toString()

        var dateFormat = SimpleDateFormat("dd-MM-yyyy");
        var currentDateSet = dateFormat.parse(currentDate);

        if (stuentDob.isNotEmpty())
        {
            selectedDateSet = dateFormat.parse(stuentDob)

            Log.e("AddStudentsFragment","curremt: ${currentDateSet}")
            Log.e("AddStudentsFragment","selected: ${selectedDateSet}")
            difference =  currentDateSet!!.time - selectedDateSet!!.time
            seconds = difference!! /1000
            Log.d("AddStudentsFragment","seconds: ${seconds}")
        }

        if (studentName.isEmpty()) {
            showSnackar("Enter Student Name")
        }
        else if (stuentDob.isEmpty())
        {
            showSnackar("Select Student's DOB")
        }
        else if (seconds!! < 63072000)
        {
            showSnackar("Student Must Be Atleast 2 Years Old ")
        }
        else if (studentFatherName.isEmpty())
        {
            showSnackar("Enter Student's Father Name")
        }
        else if (studentMotherName.isEmpty())
        {
            showSnackar("Enter Student's Mother Name")
        }
        else
        {
            activity!!.toast("Registered")
        }
    }


    private fun showSnackar(error:String)
    {
        Snackbar.make(fragmentAddStudentsBinding!!.mainLayout,error,Snackbar.LENGTH_SHORT).show()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            var answer: String = current.format(formatter)
            currentDate = answer
            Log.d("answer", answer)
        } else {
            var date = Date()
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val answer: String = formatter.format(date)
            currentDate = answer
            Log.d("answer", answer)
        }
    }
}
