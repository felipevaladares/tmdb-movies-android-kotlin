package com.arctouch.codechallenge.home.data.cache

import com.arctouch.codechallenge.home.domain.model.Genre
import com.arctouch.codechallenge.home.domain.model.Movie

object Cache {

    var genres: List<Genre>? = null

    var movies: List<Movie>? = null

    fun cacheGenres(genres: List<Genre>) {
        Cache.genres = genres
    }

    fun cacheMovies(movies: List<Movie>) {
        Cache.movies = movies
    }
}
