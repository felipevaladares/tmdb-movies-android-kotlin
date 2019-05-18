package com.arctouch.codechallenge.home.domain.model

import com.squareup.moshi.Json

data class GenreResponse(val genres: List<Genre>)

data class Genre(val id: Int, val name: String)

data class UpcomingMoviesResponse(
    val page: Int,
    val results: List<Movie>,
    @field:Json(name = "total_pages") val totalPages: Int,
    @field:Json(name = "total_results") val totalResults: Int
)

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
