package com.arctouch.codechallenge.core.api

import com.arctouch.codechallenge.core.api.model.GenresResponse
import com.arctouch.codechallenge.core.api.model.MoviesResponse
import com.arctouch.codechallenge.core.domain.model.Movie
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    companion object {
        const val URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "1f54bd990f1cdfb230adb312546d765d"
        const val DEFAULT_LANGUAGE = "pt-BR"
        const val DEFAULT_REGION = "BR"
    }

    @GET("genre/movie/list")
    fun genresAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Deferred<Response<GenresResponse>>

    @GET("movie/upcoming")
    fun upcomingMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Long,
        @Query("region") region: String
    ): Deferred<Response<MoviesResponse>>

    @GET("movie/{id}")
    fun movieAsync(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Deferred<Response<Movie>>

    @GET("movie/{movie_id}/images")
    fun movieImafesAsync(
            @Path("movie_id") id: Long,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Deferred<Response<Movie>>
}
