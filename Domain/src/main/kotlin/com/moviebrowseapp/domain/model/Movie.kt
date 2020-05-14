package com.moviebrowseapp.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*


data class Movie(val id: Long,
                 val title: String,
                 val overview: String,
                 @SerializedName("poster_path") val posterPath: String?,
                 @SerializedName("vote_count") val voteCount: Int,
                 @SerializedName("vote_average") val voteAvg: Double,
                 @SerializedName("release_date") val releaseDate: Date) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readDouble(),
            Date(parcel.readLong())) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(overview)
        parcel.writeString(posterPath)
        parcel.writeInt(voteCount)
        parcel.writeDouble(voteAvg)
        parcel.writeLong(releaseDate.time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

    fun posterUrl(): String? {
        if (posterPath != null) {
            return "http://image.tmdb.org/t/p/w500/$posterPath"
        }

        return null
    }
}

data class MovieDiscoverResponse(val page: Int,
                                 @SerializedName("total_pages") val totalPages: Int,
                                 val results: List<Movie>)