package com.arctouch.codechallenge.movie.data.repository

import com.arctouch.codechallenge.core.api.TmdbApi
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.core.domain.model.MovieImage

class MovieImagesRepository(api: TmdbApi) {
    fun getImages(movie: Movie): List<MovieImage>? {
        //TODO load from api
        return listOf()
    }
}