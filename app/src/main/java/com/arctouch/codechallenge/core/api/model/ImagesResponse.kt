package com.arctouch.codechallenge.core.api.model

import com.arctouch.codechallenge.core.domain.model.Genre
import com.arctouch.codechallenge.core.domain.model.MovieImage

data class ImagesResponse(
        val backdrops: List<Genre>,
        val posters: List<MovieImage>)