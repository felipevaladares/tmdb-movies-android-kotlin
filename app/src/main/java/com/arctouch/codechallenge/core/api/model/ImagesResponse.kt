package com.arctouch.codechallenge.core.api.model

import com.arctouch.codechallenge.core.domain.model.MovieImage

data class ImagesResponse(
        val posters: List<MovieImage>,
        val backdrops: List<MovieImage>)