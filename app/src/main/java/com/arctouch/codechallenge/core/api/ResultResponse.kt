package com.arctouch.codechallenge.core.api

import com.arctouch.codechallenge.core.domain.model.Failure
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

class ResultResponse<R>(private val call: Call<R>) {


    fun run(): Result<R?> {


        return try {
            val response = call.execute()

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

    private fun handleResponse(response: Response<R>?, responseHandler: (R?, Throwable?) -> Unit) {
        if (response?.isSuccessful == true) responseHandler(response.body(), null)
        else {
            if (response?.code() in 400..511) responseHandler(null, HttpException(response))
            else responseHandler(response?.body(), null)
        }
    }
}