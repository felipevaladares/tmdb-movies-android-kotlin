package com.arctouch.codechallenge.home.data.repository

import com.arctouch.codechallenge.home.data.api.TmdbApi
import com.arctouch.codechallenge.home.data.api.TmdbApiFactory
import com.arctouch.codechallenge.home.domain.model.Movie

class MoviesRepository() {

    private val api: TmdbApi by lazy {
        TmdbApiFactory.buildMoviesApi()
    }

    suspend fun getUpcomingMovies(): List<Movie> {
        return try {
            val result = api.upcomingMoviesAsync(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1, TmdbApi.DEFAULT_REGION).await()
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