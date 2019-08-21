package com.arctouch.codechallenge.movie.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.core.api.TmdbApi
import com.arctouch.codechallenge.core.api.TmdbApiFactory
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.movie.data.repository.MovieImagesRepository
import com.arctouch.codechallenge.movie.domain.MovieImagesUseCase

class MovieViewModelFactory(private val movie: Movie): ViewModelProvider.Factory  {

    private val api: TmdbApi by lazy {
        TmdbApiFactory.buildTmdbApi()
    }

    private val imagesRepository = MovieImagesRepository(api)
    private val imagesUseCase = MovieImagesUseCase(imagesRepository)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(movie, imagesUseCase) as T
    }
}