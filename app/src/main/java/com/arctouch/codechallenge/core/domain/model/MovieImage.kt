package com.arctouch.codechallenge.core.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieImage(
        val width: Int,
        val height: Int,
        @field:Json(name = "file_path") val filePath: String
): Parcelable