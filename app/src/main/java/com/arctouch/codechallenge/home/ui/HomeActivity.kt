package com.arctouch.codechallenge.home.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.domain.model.Failure
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.core.extensions.setGone
import com.arctouch.codechallenge.home.presentation.HomeViewModel
import com.arctouch.codechallenge.home.presentation.HomeViewModelFactory
import com.arctouch.codechallenge.home.ui.adapter.HomeAdapter
import com.arctouch.codechallenge.movie.MovieActivity
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter
import kotlinx.android.synthetic.main.home_activity.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity


class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    private var homeAdapter: HomeAdapter? = null
    private var endlessRecyclerViewAdapter: EndlessRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        loadViewModel()
    }

    private fun loadViewModel() {
        viewModel = ViewModelProviders.of(this, HomeViewModelFactory()).get(HomeViewModel::class.java)


        viewModel.failure.observe(this, Observer {
            val failure = it ?: return@Observer
            notifyFailure(failure)
        })

        viewModel.movies.observe(this, Observer {
            val movies = it ?: return@Observer
            loadMoviesAdapter(movies)
        })

        viewModel.loadMovies(true)
    }

    private fun loadMoviesAdapter(movies: List<Movie>) {
        if (homeAdapter == null) {
            createAdapter(movies.toMutableList())
        } else {
            updateAdapter(movies)
        }
    }

    private fun createAdapter(movies: MutableList<Movie>) {
        homeAdapter = HomeAdapter(movies, object : HomeAdapter.HomeAdapterListener {
            override fun onMovieClick(movie: Movie) {
                startMovieActivity(movie)
            }
        })

        endlessRecyclerViewAdapter = EndlessRecyclerViewAdapter(this, homeAdapter) {
            viewModel.loadMovies()
        }

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager = GridLayoutManager(this, 3)
        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 2)
        }
        recyclerView.adapter = endlessRecyclerViewAdapter
        progressBar.setGone()
    }

    private fun updateAdapter(movies: List<Movie>) {
        homeAdapter?.insertItems(movies)
        endlessRecyclerViewAdapter?.onDataReady(true)
    }

    private fun notifyFailure(failure: Failure) {
        when (failure) {
            is Failure.NoDataAvailable -> {
                longToast(failure.errorMessage)
                endlessRecyclerViewAdapter?.onDataReady(false)
            }

            is Failure.NetworkConnection -> {
                alert(failure.errorMessage, getString(R.string.generic_failure_title)) {
                    positiveButton(R.string.try_again) {
                        viewModel.loadMovies()
                        endlessRecyclerViewAdapter?.onDataReady(true)
                    }
                    negativeButton(R.string.cancel) {
                        dialog -> dialog.dismiss()
                        endlessRecyclerViewAdapter?.onDataReady(false)
                    }
                }.show()
            }

            is Failure.ServerError -> {
                endlessRecyclerViewAdapter?.onDataReady(false)
                alert(getString(R.string.generic_failure_description), getString(R.string.generic_failure_title)) {
                    positiveButton(R.string.try_again) { viewModel.loadMovies() }
                    negativeButton(R.string.cancel) {dialog -> dialog.dismiss() }
                }.show()
            }
        }
    }

    private fun startMovieActivity(movie: Movie) {
        startActivity<MovieActivity>(MovieActivity.ARG_MOVIE to movie)
    }
}
