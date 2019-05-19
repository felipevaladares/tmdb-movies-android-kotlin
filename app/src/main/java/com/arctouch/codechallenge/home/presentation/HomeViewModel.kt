package com.arctouch.codechallenge.home.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.core.BaseViewModel
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

    fun loadMovies(useCache: Boolean = true) {
        if (!useCache) currentPage++

        viewModelScope.launch(Dispatchers.IO) {
           _movies.postValue(moviesUseCase.getUpcoming(useCache, currentPage))
        }
    }
}