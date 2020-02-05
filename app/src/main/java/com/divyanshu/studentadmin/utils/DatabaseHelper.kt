package com.divyanshu.studentadmin.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE students(student_id INTEGER PRIMARY KEY AUTOINCREMENT  , student_name VARCHAR, student_image VARCHAR, student_father_name VARCHAR, student_mother_name VARCHAR);")
        Log.e("db created", "table created")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS students")
        onCreate(db)
    }


    fun insertStudents(student_name: String, student_image: String, student_father_name: String, student_mother_name: String): Boolean {
        val db = this.getWritableDatabase()
        val contentValues = ContentValues()
        contentValues.put(studentName, student_name)
        contentValues.put(studentImage, student_image)
        contentValues.put(studentFather_name, student_father_name)
        contentValues.put(studentMother_name, student_mother_name)

        val result = db.insert(TABLE_NAME, null, contentValues)
        return !result.equals(-1)
    }

    fun getData(): Cursor {
        val db = this.getWritableDatabase()
        return db.rawQuery("select * from $TABLE_NAME  ", null)
    }


    companion object {

        private val DATABASE_NAME = "students.db"
        private val DATABASE_VERSION = 1
        private val TABLE_NAME = "students"
        val studentId = "student_id"
        val studentName = "student_name"
        val studentImage = "student_image"
        val studentFather_name = "student_father_name"
        val studentMother_name = "student_mother_name"
    }


}