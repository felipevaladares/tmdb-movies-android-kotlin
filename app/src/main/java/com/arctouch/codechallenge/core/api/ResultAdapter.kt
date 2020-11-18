package com.arctouch.codechallenge.core.api

import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.domain.model.Failure
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type
import java.net.UnknownHostException

class ResultAdapter(private val responseType: Type): CallAdapter<Type, Any> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<Type>) = ResultCall(call)
}

