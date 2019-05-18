package com.arctouch.codechallenge.home.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.home.data.repository.MoviesRepository
import com.arctouch.codechallenge.home.domain.usecase.HomeMoviesUseCase

class HomeViewModelFactory: ViewModelProvider.Factory  {

    private val moviesRepository = MoviesRepository()
    private val moviesUseCase = HomeMoviesUseCase(moviesRepository)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(moviesUseCase) as T
    }
}