package com.arctouch.codechallenge.home.domain.usecase

import com.arctouch.codechallenge.home.data.repository.GenresRepository
import com.arctouch.codechallenge.home.data.repository.MoviesRepository
import com.arctouch.codechallenge.home.domain.model.Genre
import com.arctouch.codechallenge.home.domain.model.Movie
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class HomeMoviesUseCase(
        private val genresRepository: GenresRepository,
        private val moviesRepository: MoviesRepository) {

    suspend fun getUpcoming(useCache: Boolean, page: Long): List<Movie> {
        var genres = listOf<Genre>()
        var movies = listOf<Movie>()
        coroutineScope {
            async { genres = genresRepository.getGenres() }
            async { movies = moviesRepository.getUpcomingMovies(useCache, page) }
        }.await()

        loadGenres(movies, genres)

        return movies
    }

    private fun loadGenres(movies: List<Movie>, genres: List<Genre>) {
        movies.forEach { movie ->
            val movieGenres = mutableListOf<Genre>()
            movie.genreIds?.forEach { genreId ->
                val genre = genres.find { it.id == genreId }
                if (genre != null) movieGenres.add(genre)
            }
            movie.genres = movieGenres
        }
    }
}