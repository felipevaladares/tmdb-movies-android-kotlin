package com.arctouch.codechallenge.movie.data.cache

import com.arctouch.codechallenge.core.domain.model.MovieImage

object MovieImagesCache {
    val images = hashMapOf<Long, List<MovieImage>>()

    fun getImages(movieId: Long): List<MovieImage>? {
        return images[movieId]
    }

    fun cacheImages(movieId: Long, images: List<MovieImage>) {
        MovieImagesCache.images[movieId] = images
    }
}