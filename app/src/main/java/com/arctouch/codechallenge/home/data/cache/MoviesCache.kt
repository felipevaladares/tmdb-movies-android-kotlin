package com.arctouch.codechallenge.home.data.cache

import com.arctouch.codechallenge.core.domain.model.Genre
import com.arctouch.codechallenge.core.domain.model.Movie

object MoviesCache {

    var genres: List<Genre>? = null

    var movies: List<Movie>? = null

    fun cacheGenres(genres: List<Genre>) {
        MoviesCache.genres = genres
    }

    fun cacheMovies(movies: List<Movie>) {
        MoviesCache.movies = movies
    }
}
