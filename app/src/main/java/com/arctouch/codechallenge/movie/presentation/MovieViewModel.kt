package com.arctouch.codechallenge.movie.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arctouch.codechallenge.core.BaseViewModel
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.core.domain.model.MovieImage
import com.arctouch.codechallenge.movie.domain.MovieImagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(
        val movie: Movie,
        private val imagesUseCase: MovieImagesUseCase): BaseViewModel() {

    private val _images = MutableLiveData<List<MovieImage>>()
    val images: LiveData<List<MovieImage>>
        get() = _images

    fun loadImages() {
        viewModelScope.launch(Dispatchers.IO) {
            _images.postValue(imagesUseCase.getImages(movie))
        }
    }
}