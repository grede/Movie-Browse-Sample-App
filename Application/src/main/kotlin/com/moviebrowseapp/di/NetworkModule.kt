package com.moviebrowseapp.di

import com.moviebrowseapp.BuildConfig
import com.moviebrowseapp.data.api.ApiFactory
import com.moviebrowseapp.data.network.PicassoImageLoader
import com.moviebrowseapp.domain.api.MovieDbApi
import com.moviebrowseapp.domain.network.ImageLoader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


/**
 * Module that holds all network related objects
 */
var networkModule = Kodein.Module(name = "networkModule") {
    bind<ApiFactory>() with singleton { ApiFactory(instance()) }
    bind<MovieDbApi>() with singleton { instance<ApiFactory>().create<MovieDbApi>(BuildConfig.MOVIE_DB_API_URL) }

    bind<OkHttpClient>() with singleton { OkHttpClient.Builder().addInterceptor(instance<HttpLoggingInterceptor>()).build() }
    bind<HttpLoggingInterceptor>() with singleton { HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE) }

    bind<Picasso>() with provider { Picasso.Builder(instance()).build() }
    bind<ImageLoader>() with singleton { PicassoImageLoader(instance()) }
}