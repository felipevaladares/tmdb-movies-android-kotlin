package com.arctouch.codechallenge.movie.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.core.domain.model.MovieImage
import com.arctouch.codechallenge.movie.presentation.MovieViewModel
import com.arctouch.codechallenge.movie.ui.adapter.MovieImagesAdapter
import kotlinx.android.synthetic.main.movie_images_fragment.*

/**
 * Created by Felipe Valadares on 18/05/2019
 *
 * A fragment to show the getPosters from a movie.
 */
class MovieImagesFragment : Fragment() {

    private var movie: Movie? = null
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable(ARG_MOVIE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_images_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(MovieViewModel::class.java)
        viewModel.images.observe(this, Observer {
            val images = it ?: return@Observer
            loadImages(images)
        })

        viewModel.loadImages()
    }

    private fun loadImages(images: List<MovieImage>) {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewImages.layoutManager = layoutManager
        recyclerViewImages.adapter = MovieImagesAdapter(images)
    }

    companion object {
        private const val ARG_MOVIE = "movie"

        @JvmStatic
        fun newInstance(movie: Movie): MovieImagesFragment {
            return MovieImagesFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MOVIE, movie)
                }
            }
        }
    }
}