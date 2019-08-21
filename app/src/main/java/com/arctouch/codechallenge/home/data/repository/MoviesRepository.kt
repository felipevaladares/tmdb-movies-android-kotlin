package com.arctouch.codechallenge.home.data.repository

import com.arctouch.codechallenge.core.api.TmdbApi
import com.arctouch.codechallenge.core.domain.model.Either
import com.arctouch.codechallenge.core.domain.model.Failure
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.home.data.cache.MoviesCache
import java.net.UnknownHostException

class MoviesRepository(private val api: TmdbApi) {

    suspend fun getUpcomingMoviesFromApi(page: Long): Either<Failure, List<Movie>> {
        return try {
            val result = api.upcomingMoviesAsync(page = page).await()
            val resultBody = result.body()
            when {
                result.isSuccessful && resultBody?.results?.isNotEmpty() == true -> Either.Right(resultBody.results)
                result.isSuccessful && resultBody?.results?.isEmpty() == true -> Either.Left(Failure.NoDataAvailable())
                else -> Either.Left(Failure.RequestError())
            }
        } catch (ex: UnknownHostException) {
            ex.printStackTrace()
            Either.Left(Failure.NetworkConnection())
        } catch (ex: Exception) {
            ex.printStackTrace()
            Either.Left(Failure.RequestError())
        }
    }

    fun getUpcomingMoviesFromCache(): List<Movie> {
        return MoviesCache.movies
    }

    fun saveToCache(movies: List<Movie>) {
        MoviesCache.cacheMovies(MoviesCache.movies.union(movies).toList())
    }
}