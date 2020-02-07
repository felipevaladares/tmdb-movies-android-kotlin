package com.arctouch.codechallenge.movie.ui

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.domain.model.MovieImage
import com.arctouch.codechallenge.core.extensions.setGone
import com.arctouch.codechallenge.movie.presentation.MovieViewModel
import com.arctouch.codechallenge.movie.ui.adapter.MovieImagesAdapter
import kotlinx.android.synthetic.main.movie_images_fragment.*

/**
 * Created by Felipe Valadares on 18/05/2019
 *
 * A fragment to show the images from a movie.
 */
class MovieImagesFragment : androidx.fragment.app.Fragment() {

    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_images_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        viewModel.images.observe(viewLifecycleOwner, Observer {
            val images = it ?: return@Observer
            loadImages(images)
            progressBar.setGone()
        })

        viewModel.loadImages()
    }

    private fun loadImages(images: List<MovieImage>) {
        recyclerViewImages.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewImages.adapter = MovieImagesAdapter(images)
    }
}