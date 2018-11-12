package com.narunas.photoalbumviewer.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.gson.Gson
import com.narunas.photoalbumviewer.gson.ImageData
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


enum class UI_VERSION {
    none,
    single,
    tabs,
    pager
}

class CommonViewModel: ViewModel() {




    var SOURCE_URL = "http://jsonplaceholder.typicode.com/photos"



    companion object {

        val TAG:String = CommonViewModel::class.java.simpleName
        val AlbumData: MutableLiveData<HashMap<Int, ArrayList<ImageData>>> = MutableLiveData()
        val ImageInReview: MutableLiveData<ImageData> = MutableLiveData()
        val AlbumInReview: MutableLiveData<ArrayList<ImageData>> = MutableLiveData()
        val ErrorData: MutableLiveData<String> = MutableLiveData()

        /** UI switch
         *  here we can swith from a single view to a pager view
         *  This should reside in either a setting menu and shared preferences
         *  **/
//        var APP_UI = UI_VERSION.pager
        var APP_UI = UI_VERSION.single
        /** end switch **/
    }


    init {


    }

    /**
     *  MUST be called from a separate thread
     *  @params = nothing
     */
    fun buildCatalog() {

            val resources: StringBuffer? = fetchJsonData()
            val gson = Gson()
            val imageArray: Array<ImageData> = gson.fromJson(resources.toString(), Array<ImageData>::class.java)

            Log.d(TAG, " ---------- data size ${imageArray.size}")

            val map = HashMap<Int, ArrayList<ImageData>>()
            val iterator = imageArray.iterator()

            iterator.forEach {

                val key = it.albumId
                if(map.containsKey(key)) {
                    map.getValue(key).add(it)
                } else {
                    val value = ArrayList<ImageData>()
                    value.add(it)
                    map.put(key, value)
                }

            }

            if(!map.isEmpty()) {

                AlbumData.postValue(map)

            } else {

                ErrorData.postValue("Photo albums not available")
            }

            Log.d(TAG, "....... album size:  ${map.size}")

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