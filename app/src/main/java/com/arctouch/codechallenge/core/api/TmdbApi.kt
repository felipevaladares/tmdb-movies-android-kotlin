package com.arctouch.codechallenge.core.api

import com.arctouch.codechallenge.core.api.model.GenresResponse
import com.arctouch.codechallenge.core.api.model.ImagesResponse
import com.arctouch.codechallenge.core.api.model.MoviesResponse
import com.arctouch.codechallenge.core.domain.model.Movie
import kotlinx.coroutines.Deferred
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

interface TmdbApi {

    companion object {
        const val URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "1f54bd990f1cdfb230adb312546d765d"
        const val DEFAULT_LANGUAGE = "en-US"
        const val DEFAULT_REGION = "US"
    }

    @GET("genre/movie/list")
    fun genresAsync(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Deferred<Response<GenresResponse>>

    @GET("movie/upcoming")
    suspend fun upcomingMoviesAsync(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = DEFAULT_LANGUAGE,
        @Query("page") page: Long,
        @Query("region") region: String? = null
    ): Result<MoviesResponse>

    @GET("movie/{id}")
    fun movieAsync(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Deferred<Response<Movie>>

    @GET("movie/{movie_id}/images")
    fun movieImagesAsync(
            @Path("movie_id") id: Long,
            @Query("api_key") apiKey: String = API_KEY
    ): Deferred<Response<ImagesResponse>>
}

/*
class MyCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                Result::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    ResultAdapter(resultType)
                }
                else -> null
            }
        }
        else -> null
    }
}*/
