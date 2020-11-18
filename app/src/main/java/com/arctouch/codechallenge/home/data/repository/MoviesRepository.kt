package com.arctouch.codechallenge.home.data.repository

import com.arctouch.codechallenge.core.BaseRepository
import com.arctouch.codechallenge.core.api.TmdbApi
import com.arctouch.codechallenge.core.domain.model.Either
import com.arctouch.codechallenge.core.domain.model.Failure
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.home.data.cache.MoviesCache
import java.lang.Exception
import java.lang.reflect.Executable
import java.net.UnknownHostException

class MoviesRepository(private val api: TmdbApi): BaseRepository() {

    suspend fun getUpcomingMoviesFromApi(page: Long): Result<List<Movie>> {

         //adapter
        //base repo com safeapicall
        //interceptor
        //try/catch direto
        //try/catch viewmodel

        val response = api.upcomingMoviesAsync(page = page)
        if (response.isSuccess) {
            val results = response.getOrNull()!!.results
            return Result.success(results)
        } else {
            return Result.failure(Exception())
        }

        /*

        val response = api.upcomingMoviesAsync(page = page)


        return when {
            response.isSuccessful && response.body()?.results?.isNotEmpty() == true -> Result.success(results)
            response.isSuccessful && response.body()?.results?.isEmpty() == true -> Result.failure(Failure.NoDataAvailable())
            else -> Result.failure(Failure.RequestError())//handle error codes
        }*/

       /* val response = safeApiCall { api.upcomingMoviesAsync(page = page) }
        val movieResponse = response.getOrElse { error ->
            return Result.failure(error)
        }
        return Result.success(movieResponse.results)*/

        /*
                return if (response.isSuccess) {
            response.map {
                it.results
            }
        } else {
            Result.failure(Failure.RequestError())
        }

         */

       /* return if (response.isSuccessful) {
            Result.success(response.body()!!.results)
        } else {
            Result.failure(Failure.RequestError())
        }*/

       /* return try {

            //val resultBody = result.body()
            when {
                result.isSuccess && resultBody?.results?.isNotEmpty() == true -> Result.success(resultBody.results)
                result.isSuccess && resultBody?.results?.isEmpty() == true -> Result.failure(Failure.NoDataAvailable())
                else -> Result.failure(Failure.RequestError())
            }
        } catch (ex: UnknownHostException) {
            ex.printStackTrace()
            Result.failure(Failure.NetworkConnection())
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(Failure.RequestError())
        }*/
    }

    fun getUpcomingMoviesFromCache(): List<Movie> {
        return MoviesCache.movies
    }

    fun saveToCache(movies: List<Movie>) {
        MoviesCache.cacheMovies(MoviesCache.movies.union(movies).toList())
    }
}