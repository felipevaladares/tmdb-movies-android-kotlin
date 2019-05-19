package com.arctouch.codechallenge.movie.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.home.domain.model.Movie
import com.arctouch.codechallenge.movie.ui.adapter.MovieImagesAdapter
import kotlinx.android.synthetic.main.movie_images_fragment.*

/**
 * Created by Felipe Valadares on 18/05/2019
 *
 * A fragment to show the images from a movie.
 */
class MovieImagesFragment : Fragment() {

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable(ARG_MOVIE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_images_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImages()
    }

    private fun loadImages() {
        movie?.images?.let {
            recyclerViewImages.layoutManager = GridLayoutManager(requireContext(), 3)
            recyclerViewImages.adapter = MovieImagesAdapter(it)
        }
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