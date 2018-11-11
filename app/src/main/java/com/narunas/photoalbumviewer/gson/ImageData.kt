package com.narunas.photoalbumviewer.gson

import com.google.gson.annotations.SerializedName


data class ImageData(var timeStamp: Long) {

    @SerializedName("albumId")
    var albumId: Int = -1

    @SerializedName("id")
    var imageId: Int = -1

    @SerializedName("title")
    var title: String = ""

    @SerializedName("url")
    var mainUrl: String = ""

    @SerializedName("thumbnailUrl")
    var thumbUrl: String = ""
}