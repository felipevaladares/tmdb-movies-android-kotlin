package com.arctouch.codechallenge.home.data.repository

import com.arctouch.codechallenge.core.api.TmdbApi
import com.arctouch.codechallenge.core.domain.model.Genre
import com.arctouch.codechallenge.home.data.cache.GenresCache

class GenresRepository(private val api: TmdbApi) {

    suspend fun getGenres(): List<Genre> {
        return GenresCache.genres ?: getGenresFromApi()
    }

    private suspend fun getGenresFromApi(): List<Genre> {
        return try {
            val result = api.genresAsync().await()
            val resultBody = result.body()
            if (result.isSuccessful && resultBody != null) {
                GenresCache.cacheGenres(resultBody.genres)
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