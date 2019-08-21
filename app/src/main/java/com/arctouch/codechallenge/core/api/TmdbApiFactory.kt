package com.arctouch.codechallenge.core.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object TmdbApiFactory {

    fun buildTmdbApi(): TmdbApi {
        return Retrofit.Builder()
                .baseUrl(TmdbApi.URL)
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(TmdbApi::class.java)
    }
}
