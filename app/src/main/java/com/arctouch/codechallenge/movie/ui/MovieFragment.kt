package com.arctouch.codechallenge.movie.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.core.extensions.buildBackdropUrl
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_fragment.*

/**
 * Created by Felipe Valadares on 18/05/2019
 *
 * A fragment to show the basic details from a movie.
 */
class MovieFragment : androidx.fragment.app.Fragment() {

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable(ARG_MOVIE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMovieDetails()
    }

    private fun loadMovieDetails() {
        textViewTitle.text = movie?.title ?: getString(R.string.not_available)
        textViewOverview.text = movie?.overview ?: getString(R.string.not_available)
        textViewReleaseDate.text = movie?.releaseDate ?: getString(R.string.not_available)

        textViewGenres.text = movie?.genres?.joinToString(separator = ", ") { it.name } ?: getString(R.string.not_available)

        Glide.with(this)
                .load(movie?.buildBackdropUrl())
                .into(imageViewBackdrop)
    }

    companion object {
        private const val ARG_MOVIE = "movie"

        @JvmStatic
        fun newInstance(movie: Movie): MovieFragment {
            return MovieFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MOVIE, movie)
                }
            }
        }
    }
}