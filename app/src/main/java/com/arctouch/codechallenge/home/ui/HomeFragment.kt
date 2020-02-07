package com.arctouch.codechallenge.home.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.domain.model.Failure
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.core.extensions.setGone
import com.arctouch.codechallenge.home.presentation.HomeViewModel
import com.arctouch.codechallenge.home.presentation.HomeViewModelFactory
import com.arctouch.codechallenge.home.ui.adapter.HomeAdapter
import com.arctouch.codechallenge.movie.MovieActivity
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter
import kotlinx.android.synthetic.main.home_fragment.recyclerView
import kotlinx.android.synthetic.main.movie_images_fragment.progressBar
import org.jetbrains.anko.alert
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity

/**
 * Created by Felipe Valadares on 19/05/2019
 *
 * A fragment to show a list off movies.
 */
class HomeFragment : androidx.fragment.app.Fragment() {

    private lateinit var viewModel: HomeViewModel

    private var homeAdapter: HomeAdapter? = null
    private var endlessRecyclerViewAdapter: EndlessRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), HomeViewModelFactory()).get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadViewModel()
    }

    private fun loadViewModel() {
        viewModel.failure.observe(viewLifecycleOwner, Observer {
            val failure = it ?: return@Observer
            notifyFailure(failure)
        })

        viewModel.movies.observe(viewLifecycleOwner, Observer {
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

        endlessRecyclerViewAdapter = EndlessRecyclerViewAdapter(homeAdapter) {
            viewModel.loadMovies()
        }

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager = GridLayoutManager(
                requireContext(), 3)
        } else {
            recyclerView.layoutManager = GridLayoutManager(
                requireContext(), 2)
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
                context?.run { longToast(failure.errorMessage) }
                endlessRecyclerViewAdapter?.onDataReady(false)
            }

            is Failure.NetworkConnection -> {
                context?.run {
                    alert(failure.errorMessage, getString(R.string.generic_failure_title)) {
                        positiveButton(R.string.try_again) {
                            viewModel.loadMovies()
                            endlessRecyclerViewAdapter?.onDataReady(true)
                        }
                        negativeButton(R.string.cancel) { dialog ->
                            dialog.dismiss()
                            endlessRecyclerViewAdapter?.onDataReady(false)
                        }
                    }.show()
                }
            }

            is Failure.ServerError -> {
                endlessRecyclerViewAdapter?.onDataReady(false)
                context?.run {
                    alert(getString(R.string.generic_failure_description), getString(R.string.generic_failure_title)) {
                        positiveButton(R.string.try_again) { viewModel.loadMovies() }
                        negativeButton(R.string.cancel) {dialog -> dialog.dismiss() }
                    }.show()
                }
            }
        }
    }

    private fun startMovieActivity(movie: Movie) {
        context?.run {
            startActivity<MovieActivity>(MovieActivity.ARG_MOVIE to movie)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.failure.removeObservers(this)
    }
}