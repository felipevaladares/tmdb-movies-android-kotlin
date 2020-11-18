package com.arctouch.codechallenge.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arctouch.codechallenge.core.BaseViewModel
import com.arctouch.codechallenge.core.domain.model.Failure
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.core.utils.EspressoIdlingResource
import com.arctouch.codechallenge.home.domain.HomeMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

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
            runCatching {
                moviesUseCase.getUpcoming(currentPage, useCache)
            }.onFailure { error ->
                handleFailure(error)
            }.onSuccess { result ->
                result.map {
                    handleSuccess(it)
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                moviesUseCase.getUpcoming(currentPage, useCache).fold(::handleSuccess, ::handleFailure)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    fun handleSuccess(movies: List<Movie>) {
        _movies.postValue(movies)
    }

    fun handleFailure(failure: Throwable) {
        _failure.postValue(failure as Failure)
    }
}