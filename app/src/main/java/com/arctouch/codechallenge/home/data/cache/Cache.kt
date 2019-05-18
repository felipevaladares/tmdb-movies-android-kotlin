package com.arctouch.codechallenge.home.data.cache

import com.arctouch.codechallenge.home.domain.model.Genre

object Cache {

    var genres: List<Genre>? = null

    fun cacheGenres(genres: List<Genre>) {
        Cache.genres = genres
    }
}
