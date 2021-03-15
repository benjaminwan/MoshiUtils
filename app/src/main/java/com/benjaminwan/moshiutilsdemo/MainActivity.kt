package com.benjaminwan.moshiutilsdemo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.benjaminwan.moshi.utils.*
import com.benjaminwan.moshiutilsdemo.models.Student
import com.benjaminwan.moshiutilsdemo.models.Teacher

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val TAG = "MoshiUtils"
        const val SP_NAME = "moshi_utils_demo"
        const val SP_TEACHER = "sp_teacher"
        const val SP_TEACHER_LIST = "sp_teacher_list"
        const val SP_TEACHER_MAP = "sp_teacher_map"
    }

    private val studentDemo = Student("studentDemo", 10, 1)
    private val studentList = (1..5).map { Student("student$it", 10 + it, it) }.toList()
    private val studentMap = (1..5).map { Pair(it, Student("student$it", 10 + it, it)) }.toMap()

    private var spTeacherDemo: Teacher by App.instance
        .getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        .moshiAny<Teacher>(SP_TEACHER, Teacher("teacherDemo", 30, 2))

    private var spTeacherList: List<Teacher> by App.instance
        .getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        .moshiList(SP_TEACHER_LIST, emptyList())

    private var spTeacherMap: Map<Int, Teacher> by App.instance
        .getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        .moshiMap(SP_TEACHER_MAP, emptyMap())

    private val testMoshiAnyBtn get() = findViewById<Button>(R.id.testMoshiAnyBtn)
    private val testMoshiListBtn get() = findViewById<Button>(R.id.testMoshiListBtn)
    private val testMoshiMapBtn get() = findViewById<Button>(R.id.testMoshiMapBtn)
    private val testSPAnyBtn get() = findViewById<Button>(R.id.testSPAnyBtn)
    private val testSPListBtn get() = findViewById<Button>(R.id.testSPListBtn)
    private val testSPMapBtn get() = findViewById<Button>(R.id.testSPMapBtn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testMoshiAnyBtn.setOnClickListener(this)
        testMoshiListBtn.setOnClickListener(this)
        testMoshiMapBtn.setOnClickListener(this)
        testSPAnyBtn.setOnClickListener(this)
        testSPListBtn.setOnClickListener(this)
        testSPMapBtn.setOnClickListener(this)
    }

    override fun onDestroy() {

        super.onDestroy()
    }

    override fun onClick(view: View?) {
        view ?: return
        when (view.id) {
            R.id.testMoshiAnyBtn -> testMoshiAny()
            R.id.testMoshiListBtn -> testMoshiList()
            R.id.testMoshiMapBtn -> testMoshiMap()
            R.id.testSPAnyBtn -> testSharedPreferencesAny()
            R.id.testSPListBtn -> testSharedPreferencesList()
            R.id.testSPMapBtn -> testSharedPreferencesMap()
            else -> {
            }
        }
    }

    private fun testMoshiAny() {
        val studentJson = toJsonByMoshi(studentDemo)
        Log.i(TAG, "studentJson=$studentJson")
        assert(studentJson == """{"name":"studentDemo","age":10,"classRom":1}""")

        val studentObj = fromJsonToAnyByMoshi<Student>(studentJson)
        Log.i(TAG, "studentObj=$studentObj")
        assert(studentObj == studentDemo)

        val studentErr = fromJsonToAnyByMoshi<Teacher>(studentJson)
        Log.i(TAG, "studentErr=$studentErr")
        assert(studentErr == null)
    }

    private fun testMoshiList() {
        val studentListJson = toJsonByMoshi(studentList)
        Log.i(TAG, "studentListJson=$studentListJson")
        assert(studentListJson == """[{"name":"student1","age":11,"classRom":1},{"name":"student2","age":12,"classRom":2},{"name":"student3","age":13,"classRom":3},{"name":"student4","age":14,"classRom":4},{"name":"student5","age":15,"classRom":5}]""")

        val studentListObj = fromJsonToListByMoshi<Student>(studentListJson)
        Log.i(TAG, "studentListObj=$studentListObj")
        assert(studentListObj == studentList)

        val studentListErr = fromJsonToListByMoshi<Teacher>(studentListJson)
        Log.i(TAG, "studentListErr=$studentListErr")
        assert(studentListErr.isEmpty())
    }

    private fun testMoshiMap() {
        val studentMapJson = toJsonByMoshi(studentMap)
        Log.i(TAG, "studentMapJson=$studentMapJson")
        assert(studentMapJson == """{"1":{"name":"student1","age":11,"classRom":1},"2":{"name":"student2","age":12,"classRom":2},"3":{"name":"student3","age":13,"classRom":3},"4":{"name":"student4","age":14,"classRom":4},"5":{"name":"student5","age":15,"classRom":5}}""")

        val studentMapObj: Map<Int, Student> = fromJsonToMapByMoshi(studentMapJson)
        Log.i(TAG, "studentMapObj=$studentMapObj")
        assert(studentMapObj == studentMap)

        val studentMapErr: Map<Int, Teacher> = fromJsonToMapByMoshi(studentMapJson)
        Log.i(TAG, "studentMapErr=$studentMapErr")
        assert(studentMapErr.isEmpty())
    }

    private fun testSharedPreferencesAny() {
        //get sp obj
        Log.i(TAG, "get spTeacherDemo=$spTeacherDemo")
        //save sp obj
        spTeacherDemo = spTeacherDemo.copy(age = spTeacherDemo.age + 1)
        Log.i(TAG, "save spTeacherDemo=$spTeacherDemo")
    }

    private fun testSharedPreferencesList() {
        //get sp list
        Log.i(TAG, "get spTeacherList=$spTeacherList")
        //save sp list
        spTeacherList = spTeacherList.toMutableList().apply {
            val size = spTeacherList.size
            add(Teacher("Teacher$size", 30 + size, size))
        }
        Log.i(TAG, "save spTeacherList=$spTeacherList")
    }

    private fun testSharedPreferencesMap() {
        //get sp map
        Log.i(TAG, "get pTeacherMap=$spTeacherMap")
        //save sp map
        spTeacherMap = spTeacherMap.toMutableMap().apply {
            val size = spTeacherMap.size
            put(size, Teacher("Teacher$size", 30 + size, size))
        }
        Log.i(TAG, "save spTeacherMap=$spTeacherMap")
    }


}