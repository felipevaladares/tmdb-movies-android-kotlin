package com.arctouch.codechallenge.core.extensions

import com.arctouch.codechallenge.core.api.TmdbApi
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.core.domain.model.MovieImage

private const val POSTER_URL = "https://image.tmdb.org/t/p/w500"
private const val BACKDROP_URL = "https://image.tmdb.org/t/p/w780"

fun Movie.buildPosterUrl(): String {
    return POSTER_URL + posterPath + "?api_key=" + TmdbApi.API_KEY
}

fun Movie.buildBackdropUrl(): String {
    return BACKDROP_URL + backdropPath + "?api_key=" + TmdbApi.API_KEY
}


fun MovieImage.buildPosterUrl(): String {
    return POSTER_URL + filePath + "?api_key=" + TmdbApi.API_KEY
}

fun MovieImage.buildBackdropUrl(): String {
    return BACKDROP_URL + filePath + "?api_key=" + TmdbApi.API_KEY
}