package com.arctouch.codechallenge.home.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseActivity
import com.arctouch.codechallenge.home.domain.model.Movie
import com.arctouch.codechallenge.home.presentation.HomeViewModel
import com.arctouch.codechallenge.home.presentation.HomeViewModelFactory
import com.arctouch.codechallenge.home.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : BaseActivity() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        loadViewModel()
    }

    private fun loadViewModel() {
        viewModel = ViewModelProviders.of(this, HomeViewModelFactory()).get(HomeViewModel::class.java)

        viewModel.movies.observe(this, Observer {
            val movies = it ?: return@Observer
            loadMoviesAdapter(movies)
        })

        viewModel.loadMovies()
    }

    private fun loadMoviesAdapter(movies: List<Movie>) {
        recyclerView.adapter = HomeAdapter(movies)
        progressBar.visibility = View.GONE
    }
}
