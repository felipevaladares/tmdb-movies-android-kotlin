package com.arctouch.codechallenge.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.movie.presentation.MovieViewModel
import com.arctouch.codechallenge.movie.presentation.MovieViewModelFactory
import com.arctouch.codechallenge.movie.ui.adapter.MoviePagerAdapter
import kotlinx.android.synthetic.main.movie_activity.tabLayout
import kotlinx.android.synthetic.main.movie_activity.toolbar
import kotlinx.android.synthetic.main.movie_activity.viewPager

class MovieActivity : AppCompatActivity() {

    private var movie: Movie? = null

    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_activity)

        movie = intent.extras?.getParcelable(ARG_MOVIE) ?: throw IllegalArgumentException("Movie argument must not be null")

        loadToolbar()
        loadViewPager()
        loadViewModel()
    }

    private fun loadViewModel() {
        viewModel = ViewModelProvider(this, MovieViewModelFactory(movie!!)).get(MovieViewModel::class.java)
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