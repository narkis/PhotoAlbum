package com.narunas.photoalbumviewer.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.gson.Gson
import com.narunas.photoalbumviewer.gson.AlbumData
import com.narunas.photoalbumviewer.gson.ImageData
import com.narunas.photoalbumviewer.gson.PhotoData
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread

class CommonViewModel: ViewModel() {

    private var SOURCE_URL = "http://jsonplaceholder.typicode.com/photos"
    companion object {

        val TAG:String = CommonViewModel::class.java.simpleName
        val AlbumData: MutableLiveData<ArrayList<AlbumData>> = MutableLiveData()
        val ImageInReview: MutableLiveData<ImageData> = MutableLiveData()
        val ErrorData: MutableLiveData<String> = MutableLiveData()
    }


    init {

    }


    fun buildCatalog() {

        thread {

            val resources: StringBuffer? = fetchJsonData()
            val gson = Gson()
            val imageArray: Array<ImageData> = gson.fromJson(resources.toString(), Array<ImageData>::class.java)
            for(i in 1..imageArray.size) {

                Log.d(TAG, imageArray[i].title)

            }
        }

    }

    fun fetchJsonData(): StringBuffer? {

        val mURL = URL(SOURCE_URL)
        var response: StringBuffer? = null

        with(mURL.openConnection() as HttpURLConnection) {

            connectTimeout = 6000

            when (responseCode) {

                HttpURLConnection.HTTP_OK -> {

                } else -> {

                    ErrorData.postValue(errorStream.read().toString())
                    return null
                }
            }


            BufferedReader(InputStreamReader(inputStream)).use {


                response = StringBuffer()
                var inputLine = it.readLine()
                while (inputLine != null) {
                    response?.append(inputLine)
                    inputLine = it.readLine()
                }

                it.close()
            }

            disconnect()

        }

        return response

    }


}