package com.arctouch.codechallenge.home.data.cache

import com.arctouch.codechallenge.home.domain.model.Genre

object Cache {

    var genres = listOf<Genre>()

    fun cacheGenres(genres: List<Genre>) {
        Cache.genres = genres
    }
}
