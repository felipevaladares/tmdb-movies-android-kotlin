package com.arctouch.codechallenge.movie

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.movie.ui.adapter.MoviePagerAdapter
import kotlinx.android.synthetic.main.movie_activity.*

class MovieActivity : AppCompatActivity() {

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_activity)

        movie = intent.extras?.getParcelable(ARG_MOVIE)
        loadToolbar()
        loadViewPager()
    }

    private fun loadToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = movie?.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadViewPager() {
        val sectionsPagerAdapter = MoviePagerAdapter(movie!!, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    companion object {
        const val ARG_MOVIE = "movie"
    }
}