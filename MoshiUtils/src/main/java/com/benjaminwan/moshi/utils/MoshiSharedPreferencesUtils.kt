package com.benjaminwan.moshi.utils

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 使用moshi+SharedPreferences 任意对象 读写代理
 * @param  key 存储key
 * @param  defValue 默认值
 * @return 返回代理
 */
inline fun <reified T> SharedPreferences.moshiAny(key: String, defValue: T) =
    object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            if (!contains(key)) {
                return defValue
            }
            val value = getString(key, "")
            if (value.isNullOrEmpty()) {
                return defValue
            }
            return fromJsonToAnyByMoshi(value) ?: defValue
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            value ?: return edit().putString(key, "").apply()
            return edit().putString(key, toJsonByMoshi(value)).apply()
        }
    }

/**
 * 使用moshi+SharedPreferences List<T>对象 读写代理
 * @param  key 存储key
 * @return 返回代理
 */
inline fun <reified T> SharedPreferences.moshiList(key: String, defList: List<T> = emptyList()) =
    object : ReadWriteProperty<Any, List<T>> {
        override fun getValue(thisRef: Any, property: KProperty<*>): List<T> {
            if (!contains(key)) {
                return defList
            }
            val value = getString(key, "")
            if (value.isNullOrEmpty()) {
                return defList
            }
            return fromJsonToListByMoshi(value) ?: defList
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: List<T>) {
            return edit().putString(key, toJsonByMoshi(value)).apply()
        }
    }

/**
 * 使用moshi+SharedPreferences Map<T1,T2>对象 读写代理
 * @param  key 存储key
 * @return 返回代理
 */
inline fun <reified T1, reified T2> SharedPreferences.moshiMap(
    key: String, defMap: Map<T1, T2> = emptyMap()
) = object : ReadWriteProperty<Any, Map<T1, T2>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Map<T1, T2> {
        if (!contains(key)) {
            return defMap
        }
        val value = getString(key, "")
        if (value.isNullOrEmpty()) {
            return defMap
        }
        return fromJsonToMapByMoshi(value) ?: defMap
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Map<T1, T2>) {
        return edit().putString(key, toJsonByMoshi(value)).apply()
    }
}