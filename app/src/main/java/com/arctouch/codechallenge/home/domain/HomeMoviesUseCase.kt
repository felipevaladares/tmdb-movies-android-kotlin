package com.arctouch.codechallenge.home.domain

import com.arctouch.codechallenge.core.domain.model.*
import com.arctouch.codechallenge.home.data.repository.GenresRepository
import com.arctouch.codechallenge.home.data.repository.MoviesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class HomeMoviesUseCase(
        private val genresRepository: GenresRepository,
        private val moviesRepository: MoviesRepository) {

    private var currentPage = 0L

    suspend fun getUpcoming(page: Long, loadFromCache: Boolean, onResult: (Either<Failure, List<Movie>>) -> Unit) {
        val movies = mutableListOf<Movie>()
        if (loadFromCache) getUpcomingFromCache(movies)
        if (page != currentPage) getUpcomingFromApi(page, movies, onResult)

        if (movies.isNotEmpty()){
            currentPage = page
            onResult(Either.Right(movies))
        }
        moviesRepository.saveToCache(movies)
    }

    private fun getUpcomingFromCache(movies: MutableList<Movie>) {
        movies.addAll(moviesRepository.getUpcomingMoviesFromCache())
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun getUpcomingFromApi(page: Long, movies: MutableList<Movie>, onResult: (Either<Failure, List<Movie>>) -> Unit) {
        var genres = listOf<Genre>()
        var apiResult = Any()
        coroutineScope {
            async { genres = genresRepository.getGenres() }
            async { apiResult = moviesRepository.getUpcomingMoviesFromApi(page) }
        }.await()

        val result = apiResult as Either<Failure, List<Movie>>
        if (result.isRight) {
            result.map {
                movies.addAll(it)
                loadGenres(movies, genres)
            }
        } else {
            onResult(result)
        }
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