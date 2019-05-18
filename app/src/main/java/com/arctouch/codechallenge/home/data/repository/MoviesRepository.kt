package com.arctouch.codechallenge.home.data.repository

import com.arctouch.codechallenge.core.api.TmdbApi
import com.arctouch.codechallenge.home.data.cache.Cache
import com.arctouch.codechallenge.home.domain.model.Movie

class MoviesRepository(private val api: TmdbApi) {

    private var currentPage = 0L

    suspend fun getUpcomingMovies(useCache: Boolean, page: Long): List<Movie> {
        val movies = mutableListOf<Movie>()

        if (useCache && Cache.movies != null) { //verify cached movies
            movies.addAll(Cache.movies!!)
        } else {
            if (page == currentPage) { //verify null cache and same page
                movies.addAll(getUpcomingMoviesFromApi(currentPage))
            }
        }

        if (page != currentPage) { //verify new page
            currentPage = page
            movies.addAll(getUpcomingMoviesFromApi(currentPage))
        }

        Cache.cacheMovies(movies)

        return movies
    }

    private suspend fun getUpcomingMoviesFromApi(page: Long): List<Movie> {
        return try {
            val result = api.upcomingMoviesAsync(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION).await()
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
}