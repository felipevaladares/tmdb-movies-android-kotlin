package com.arctouch.codechallenge.home.domain.model

import com.squareup.moshi.Json

data class Movie(
        val id: Int,
        val title: String,
        val overview: String?,
        val genres: List<Genre>?,
        @field:Json(name = "genre_ids") val genreIds: List<Int>?,
        @field:Json(name = "poster_path") val posterPath: String?,
        @field:Json(name = "backdrop_path") val backdropPath: String?,
        @field:Json(name = "release_date") val releaseDate: String?
)