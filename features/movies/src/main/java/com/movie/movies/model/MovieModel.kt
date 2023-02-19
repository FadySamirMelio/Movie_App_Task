package com.movie.movies.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.movie.cache.entity.MovieEntity

data class MovieModel(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("overview") val description: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val totalVotes: Int?,
    @SerializedName("genre_ids") val genreIdsList: ArrayList<Int>?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readInt(),
        parcel.readArrayList(Int::class.java.classLoader) as? ArrayList<Int>
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id!!)
        parcel.writeString(title)
        parcel.writeString(posterPath)
        parcel.writeString(backdropPath)
        parcel.writeString(description)
        parcel.writeValue(voteAverage)
        parcel.writeInt(totalVotes!!)
        parcel.writeList(genreIdsList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieModel> {
        override fun createFromParcel(parcel: Parcel): MovieModel {
            return MovieModel(parcel)
        }

        override fun newArray(size: Int): Array<MovieModel?> {
            return arrayOfNulls(size)
        }
    }

    fun toMovieEntity(): MovieEntity {
        return MovieEntity(
            id, title, posterPath, backdropPath, description, voteAverage, totalVotes
        )
    }

}