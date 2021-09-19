package com.katyrin.testronasit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.katyrin.testronasit.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: replaceMainFragment()
    }

    private fun replaceMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commit()
    }
}