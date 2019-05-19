package com.arctouch.codechallenge.core.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        val id: Long,
        val title: String,
        val overview: String?,
        var genres: List<Genre>?,
        var images: List<MovieImage>?,
        @field:Json(name = "genre_ids") val genreIds: List<Int>?,
        @field:Json(name = "poster_path") val posterPath: String?,
        @field:Json(name = "backdrop_path") val backdropPath: String?,
        @field:Json(name = "release_date") val releaseDate: String?
) : Parcelable