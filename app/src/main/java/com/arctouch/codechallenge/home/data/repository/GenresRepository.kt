package com.arctouch.codechallenge.home.data.repository

import com.arctouch.codechallenge.core.api.TmdbApi
import com.arctouch.codechallenge.home.data.cache.MoviesCache
import com.arctouch.codechallenge.home.domain.model.Genre

class GenresRepository(private val api: TmdbApi) {

    suspend fun getGenres(): List<Genre> {
        return MoviesCache.genres ?: getGenresFromApi()
    }

    private suspend fun getGenresFromApi(): List<Genre> {
        return try {
            val result = api.genresAsync(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE).await()
            val resultBody = result.body()
            if (result.isSuccessful && resultBody != null) {
                MoviesCache.cacheGenres(resultBody.genres)
                resultBody.genres
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