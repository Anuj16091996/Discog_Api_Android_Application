package com.example.myapplication_discorg_album.entities
import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class Album(
    val album_id:Int,
    @SerializedName("title") val title:String?,
    @SerializedName("genre") val genre:List<String>?,
    @SerializedName("label") val label:List<String>?,
    @SerializedName("thumb") val thumb_Image:String,
    @SerializedName("cover_image")val cover_Image:String

):Serializable  {
}