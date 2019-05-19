package com.arctouch.codechallenge.movie.data.repository

import com.arctouch.codechallenge.core.api.TmdbApi
import com.arctouch.codechallenge.core.api.model.ImagesResponse
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.core.domain.model.MovieImage
import com.arctouch.codechallenge.movie.data.cache.MovieImagesCache

class MovieImagesRepository(private val api: TmdbApi) {
    suspend fun getImages(movie: Movie): List<MovieImage>? {
        val images = mutableListOf<MovieImage>()
        MovieImagesCache.getImages(movie.id)?.let {
            images.addAll(it)
        }

        if (images.isEmpty()) {
            getImagesFromApi(movie.id)?.let {
                images.addAll(it)
            }
        }
        return images
    }

    private suspend fun getImagesFromApi(id: Long): List<MovieImage>? {
        return try {
            val result = api.movieImagesAsync(id, TmdbApi.API_KEY).await()
            val resultBody = result.body()
            if (result.isSuccessful && resultBody != null) {
                saveToCache(id, resultBody)
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

    private fun saveToCache(id: Long, resultBody: ImagesResponse): MutableList<MovieImage> {
        val images = (resultBody.posters + resultBody.backdrops).toMutableList().apply { shuffle() }
        MovieImagesCache.cacheImages(id, images)
        return images
    }
}