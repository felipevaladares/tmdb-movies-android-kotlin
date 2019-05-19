package com.arctouch.codechallenge.movie.domain

import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.core.domain.model.MovieImage
import com.arctouch.codechallenge.movie.data.repository.MovieImagesRepository

class MovieImagesUseCase(private val imagesRepository: MovieImagesRepository) {

    suspend fun getImages(movie: Movie): List<MovieImage>? {
        return imagesRepository.getImages(movie)
    }
}