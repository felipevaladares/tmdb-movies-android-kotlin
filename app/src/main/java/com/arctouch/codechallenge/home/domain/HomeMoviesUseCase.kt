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

        if (loadFromCache)
            getUpcomingFromCache(movies)

        if (page != currentPage) {
            currentPage = page
            val result = getUpcomingFromApi(page, movies)
            if (result.isLeft) onResult(result) //notify in case of error
        }

        if (movies.isNotEmpty())
            onResult(Either.Right(movies))

        moviesRepository.saveToCache(movies)
    }

    private fun getUpcomingFromCache(movies: MutableList<Movie>) {
        movies.addAll(moviesRepository.getUpcomingMoviesFromCache())
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getUpcomingFromApi(page: Long, movies: MutableList<Movie>): Either<Failure, List<Movie>> {
        var genres = listOf<Genre>()
        var result: Either<Failure, List<Movie>>? = null
        coroutineScope {
            async { genres = genresRepository.getGenres() }
            async { result = moviesRepository.getUpcomingMoviesFromApi(page) }
        }.await()

        return if (result?.isRight == true) {// verify success
            result!!.map {
                movies.addAll(it)
                loadGenres(movies, genres)
            }
            Either.Right(movies)
        } else {
            result ?: Either.Left(Failure.RequestError())
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