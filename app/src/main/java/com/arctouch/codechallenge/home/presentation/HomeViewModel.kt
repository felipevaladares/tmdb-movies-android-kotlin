package com.arctouch.codechallenge.home.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.base.BaseViewModel
import com.arctouch.codechallenge.home.domain.model.Movie
import com.arctouch.codechallenge.home.domain.usecase.HomeMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class HomeViewModel(private val moviesUseCase: HomeMoviesUseCase): BaseViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    fun loadMovies() {
        viewModelScope.launch(Dispatchers.IO) {
           _movies.postValue(moviesUseCase.getUpcoming())
        }
    }
}