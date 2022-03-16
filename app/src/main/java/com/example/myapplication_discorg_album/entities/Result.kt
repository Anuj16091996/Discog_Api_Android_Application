package com.example.myapplication_discorg_album.entities

import com.google.gson.annotations.SerializedName

data class ResultOfDiscords(
   @SerializedName("results") val owner :MutableList<Album>) {

}