package com.benjaminwan.moshiutilsdemo.models

data class Teacher(
    override var name: String,
    override var age: Int,
    var grade: Int,
) : People(name, age)
