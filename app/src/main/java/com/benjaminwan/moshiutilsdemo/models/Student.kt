package com.benjaminwan.moshiutilsdemo.models

data class Student(
    override var name: String,
    override var age: Int,
    var classRom: Int,
) : People(name, age)