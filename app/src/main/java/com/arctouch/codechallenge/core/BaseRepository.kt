package com.arctouch.codechallenge.core

import com.arctouch.codechallenge.core.domain.model.Failure
import retrofit2.Response
import java.net.UnknownHostException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = safeApiResult(call)

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>) : Result<T?> {
        return try {
            val response = call.invoke()

            if(response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(Failure.RequestError())
            }
        } catch (ex: UnknownHostException) {
            ex.printStackTrace()
            Result.failure(Failure.NetworkConnection())
        } catch (error : Exception) {
            Result.failure(error)
        }
    }
}