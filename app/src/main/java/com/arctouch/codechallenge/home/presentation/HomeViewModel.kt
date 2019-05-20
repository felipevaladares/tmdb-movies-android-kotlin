package com.arctouch.codechallenge.home.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.core.BaseViewModel
import com.arctouch.codechallenge.core.domain.model.Failure
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.home.domain.HomeMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class HomeViewModel(private val moviesUseCase: HomeMoviesUseCase): BaseViewModel() {

    private var currentPage = 1L

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _failure = MutableLiveData<Failure>()
    val failure: LiveData<Failure>
        get() = _failure

    fun loadMovies(useCache: Boolean = false) {
        if (!useCache) currentPage++

        viewModelScope.launch(Dispatchers.IO) {
           moviesUseCase.getUpcoming(currentPage, useCache) { result ->
               result.either(::handleFailure, ::handleSuccess)
           }
        }
    }

    fun handleSuccess(movies: List<Movie>) {
        _movies.postValue(movies)
    }

    fun handleFailure(failure: Failure) {
        _failure.postValue(failure)
    }
}