package com.katyrin.testronasit.model.storage

interface Storage {
    fun getMeasure(): String
    fun setMeasure(isMetric: Boolean)
    fun getLanguage(): String
    fun isMetric(): Boolean
}