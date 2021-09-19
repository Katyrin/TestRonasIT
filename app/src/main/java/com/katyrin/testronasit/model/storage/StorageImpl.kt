package com.katyrin.testronasit.model.storage

import android.content.Context
import android.content.SharedPreferences
import com.katyrin.testronasit.R

class StorageImpl(private val context: Context) : Storage {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    override fun setMeasure(isMetric: Boolean): Unit =
        prefs.edit()
            .apply { putBoolean(IS_METRICS, isMetric) }
            .apply()

    override fun getMeasure(): String = if (isMetric()) METRIC else IMPERIAL

    override fun getLanguage(): String =
        prefs.getString(LANGUAGE, getDefaultLanguage()) ?: getDefaultLanguage()

    override fun isMetric(): Boolean = prefs.getBoolean(IS_METRICS, true)

    private fun getDefaultLanguage(): String = context.getString(R.string.language)

    private companion object {
        const val IS_METRICS = "IS_METRICS"
        const val METRIC = "metric"
        const val IMPERIAL = "imperial"
        const val LANGUAGE = "LANGUAGE"
    }
}