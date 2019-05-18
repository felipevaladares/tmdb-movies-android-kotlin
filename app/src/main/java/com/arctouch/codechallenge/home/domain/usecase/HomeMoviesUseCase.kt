package com.arctouch.codechallenge.home.domain.usecase

import com.arctouch.codechallenge.home.data.repository.MoviesRepository
import com.arctouch.codechallenge.home.domain.model.Movie

class HomeMoviesUseCase(private val moviesRepository: MoviesRepository) {

    suspend fun getUpcoming(): List<Movie> {
        return moviesRepository.getUpcomingMovies()
    }
}