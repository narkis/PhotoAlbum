package com.narunas.photoalbumviewer.gson

import com.google.gson.annotations.SerializedName


data class ImageData(var timeStamp: Long) {

    @SerializedName("id")
    var imageId: Int = -1

    @SerializedName("title")
    var title: String = ""

    @SerializedName("url")
    var mainUrl: String = ""

    @SerializedName("thumbnailUrl")
    var thumbUrl: String = ""


    var parentAlbumId: Int = -1
}