package com.arctouch.codechallenge.home.data.repository

import com.arctouch.codechallenge.core.api.TmdbApi
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.home.data.cache.MoviesCache

class MoviesRepository(private val api: TmdbApi) {

    private var currentPage = 0L

    suspend fun getUpcomingMovies(useCache: Boolean, page: Long): List<Movie> {
        val movies = mutableListOf<Movie>()

        if (useCache) movies.addAll(getUpcomingMoviesFromCache())
        if (page != currentPage) movies.addAll(getUpcomingMoviesFromApi(page))
        if (movies.isNotEmpty()) currentPage = page

        saveToCache(movies)

        return movies
    }

    private suspend fun getUpcomingMoviesFromApi(page: Long): List<Movie> {
        return try {
            val result = api.upcomingMoviesAsync(page = page).await()
            val resultBody = result.body()
            if (result.isSuccessful && resultBody != null) {
                resultBody.results
            } else {
                //TODO handle api error
                emptyList()
            }
        } catch (ex: Exception) {
            //TODO handle api error
            ex.printStackTrace()
            emptyList()
        }
    }

    private fun getUpcomingMoviesFromCache(): List<Movie> {
        return MoviesCache.movies
    }

    private fun saveToCache(movies: List<Movie>) {
        MoviesCache.cacheMovies(MoviesCache.movies.union(movies).toList())
    }
}