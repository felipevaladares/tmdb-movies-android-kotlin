package com.arctouch.codechallenge.home.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.core.api.TmdbApi
import com.arctouch.codechallenge.core.api.TmdbApiFactory
import com.arctouch.codechallenge.home.data.repository.GenresRepository
import com.arctouch.codechallenge.home.data.repository.MoviesRepository
import com.arctouch.codechallenge.home.domain.usecase.HomeMoviesUseCase

class HomeViewModelFactory: ViewModelProvider.Factory  {

    private val api: TmdbApi by lazy {
        TmdbApiFactory.buildTmdbApi()
    }

    private val genresRepository = GenresRepository(api)
    private val moviesRepository = MoviesRepository(api)
    private val moviesUseCase = HomeMoviesUseCase(genresRepository, moviesRepository)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(moviesUseCase) as T
    }
}