package com.arctouch.codechallenge.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.home.ui.HomeFragment


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        loadHomeFragment()
    }

    private fun loadHomeFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment())
                .commit()
    }
}
