package com.moviebrowseapp.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Factory responsible for API implementation creation
 */
class ApiFactory(private val okHttpClient: OkHttpClient) {

    private val factoryMap: MutableMap<String, Retrofit> = hashMapOf()

    private fun createFactory(baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()
    }

    fun <D> create(baseUrl: String, clazz: Class<D>): D {
        return factoryMap.getOrPut(baseUrl) { createFactory(baseUrl) }.create(clazz)
    }

    inline fun <reified D> create(baseUrl: String): D {
        return create(baseUrl, D::class.java)
    }
}