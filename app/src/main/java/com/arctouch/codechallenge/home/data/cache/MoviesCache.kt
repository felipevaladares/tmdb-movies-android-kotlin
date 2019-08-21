package com.arctouch.codechallenge.home.data.cache

import com.arctouch.codechallenge.core.domain.model.Movie

object MoviesCache {
    var movies = emptyList<Movie>()

    fun cacheMovies(movies: List<Movie>) {
        MoviesCache.movies = movies
    }
}
