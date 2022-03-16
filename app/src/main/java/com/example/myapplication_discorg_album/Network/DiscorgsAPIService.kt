package com.example.myapplication_discorg_album.Network

import com.example.myapplication_discorg_album.entities.ResultOfDiscords
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.discogs.com/"
private val retrofit =
    Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build()


interface DiscorgsAPIService {
    @GET("/database/search")
    fun getSearchResults(
        @Query("key") key: String,
        @Query("secret") secret: String,
        @Query("artist") search: String,
        @Query("country") country: String
    ): Call<ResultOfDiscords>
}

object DiscorgsAPI {
    val retrofitService: DiscorgsAPIService by lazy {
        retrofit.create(DiscorgsAPIService::class.java)

    }
}