# MoshiUtils
[![Build Status](https://jitpack.io/v/benjaminwan/MoshiUtils.svg)](https://jitpack.io/v/benjaminwan/MoshiUtils.svg)
[![Issue](https://img.shields.io/github/issues/benjaminwan/MoshiUtils.svg)](https://github.com/benjaminwan/MoshiUtils/issues)
[![Star](https://img.shields.io/github/stars/benjaminwan/MoshiUtils.svg)](https://github.com/benjaminwan/MoshiUtils)

### Introduction
Moshi Utils, written in kotlin

### ChangeLog
- v1.0.1: kotlin 1.4.30
- v1.0.2: kotlin 1.5.10
- v1.0.3: kotlin 1.5.20

### Installation
0. root build.gradle add
```groovy
repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io" }
    }
```

1. add dependencies
```groovy
dependencies {
    implementation 'com.github.benjaminwan:MoshiUtils:1.0.2'
}
```

### Usage
#### Object
##### define obj
```kotlin
val studentDemo = Student("studentDemo", 10, 1)
```

##### obj to json string
```kotlin
val studentJson = toJsonByMoshi(studentDemo)
```

##### json string to obj
```kotlin
val studentObj = fromJsonToAnyByMoshi<Student>(studentJson)
```

#### List
##### define a list
```kotlin
val studentList = (1..5).map { Student("student$it", 10 + it, it) }.toList()
```

##### list to json string
```kotlin
val studentListJson = toJsonByMoshi(studentList)
```

##### json string to list
```kotlin
val studentListObj = fromJsonToListByMoshi<Student>(studentListJson)
```

#### Map
##### define a map
```kotlin
val studentMap = (1..5).map { Pair(it, Student("student$it", 10 + it, it)) }.toMap()
```

##### map to json string
```kotlin
val studentMapJson = toJsonByMoshi(studentMap)
```

##### json string to map
```kotlin
val studentMapObj: Map<Int, Student> = fromJsonToMapByMoshi(studentMapJson)
```

#### SharedPreferences Delegate

##### SharedPreferences Object
```kotlin
var spTeacherDemo: Teacher by App.instance
    .getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    .moshiAny<Teacher>(SP_TEACHER, Teacher("teacherDemo", 30, 2))
//get sp
val temp = spTeacherDemo
//put sp
spTeacherDemo = temp
```

##### SharedPreferences List
```kotlin
var spTeacherList: List<Teacher> by App.instance
    .getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    .moshiList(SP_TEACHER_LIST, emptyList())
//get sp
val temp = spTeacherList
//put sp
spTeacherList = temp
```

##### SharedPreferences Map
```kotlin
var spTeacherMap: Map<Int, Teacher> by App.instance
    .getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    .moshiMap(SP_TEACHER_MAP, emptyMap())
//get sp
val temp = spTeacherMap
//put sp
spTeacherMap = temp
```