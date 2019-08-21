package com.arctouch.codechallenge.home.data.cache

import com.arctouch.codechallenge.core.domain.model.Genre

object GenresCache {

    var genres: List<Genre>? = null

    fun cacheGenres(genres: List<Genre>) {
        GenresCache.genres = genres
    }
}
