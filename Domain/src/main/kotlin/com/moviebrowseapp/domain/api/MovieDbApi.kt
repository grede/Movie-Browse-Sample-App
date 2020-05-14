package com.moviebrowseapp.domain.api

import com.moviebrowseapp.domain.model.MovieDiscoverResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieDbApi {

    @GET("/3/discover/movie")
    fun discoverMovies(@Query("api_key") apiKey: String,
                       @Query("page") page: Int,
                       @Query("sort_by") sortBy: String,
                       @Query("release_date.lte") releasedBefore: String? = null): Single<MovieDiscoverResponse>
}

