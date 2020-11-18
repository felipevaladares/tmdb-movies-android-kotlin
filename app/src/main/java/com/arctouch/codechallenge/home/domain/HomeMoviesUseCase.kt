package com.arctouch.codechallenge.home.domain

import com.arctouch.codechallenge.core.domain.model.*
import com.arctouch.codechallenge.home.data.repository.GenresRepository
import com.arctouch.codechallenge.home.data.repository.MoviesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

class HomeMoviesUseCase(
        private val genresRepository: GenresRepository,
        private val moviesRepository: MoviesRepository) {

    private var currentPage = 0L

    suspend fun getUpcoming(page: Long, loadFromCache: Boolean): Result<List<Movie>> {
        val movies = mutableListOf<Movie>()

        if (loadFromCache)
            getUpcomingFromCache(movies)

        if (page != currentPage) {
            currentPage = page
            return getUpcomingFromApi(page, movies)
        }

        moviesRepository.saveToCache(movies)

        return if (movies.isNotEmpty())
            Result.success(movies)
        else
            Result.failure(Failure.NoDataAvailable())
    }

    private fun getUpcomingFromCache(movies: MutableList<Movie>) {
        movies.addAll(moviesRepository.getUpcomingMoviesFromCache())
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getUpcomingFromApi(page: Long, movies: MutableList<Movie>): Result<List<Movie>> {
        var genres = listOf<Genre>()
        var result: Result<List<Movie>>? = null
        coroutineScope {
            async { genres = genresRepository.getGenres() }
            async { result = moviesRepository.getUpcomingMoviesFromApi(page) }
        }.await()


        return if (result?.isSuccess == true) {// verify success
            result!!.map {
                movies.addAll(it)
                loadGenres(movies, genres)
            }
            Result.success(movies)
        } else {
            result ?: Result.failure(Failure.RequestError())
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