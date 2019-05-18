package com.arctouch.codechallenge.home.data.api.model

import com.arctouch.codechallenge.home.domain.model.Movie
import com.squareup.moshi.Json

data class MoviesResponse(
        val page: Int,
        val results: List<Movie>,
        @field:Json(name = "total_pages") val totalPages: Int,
        @field:Json(name = "total_results") val totalResults: Int
)