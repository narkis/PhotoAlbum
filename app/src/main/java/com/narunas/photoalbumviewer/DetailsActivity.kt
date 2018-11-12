package com.narunas.photoalbumviewer

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.narunas.photoalbumviewer.gson.ImageData
import com.narunas.photoalbumviewer.ui.common.BaseImageView
import com.narunas.photoalbumviewer.viewmodel.CommonViewModel.Companion.ImageInReview
import kotlinx.android.synthetic.main.activity_detail.*

class DetailsActivity: AppCompatActivity() {


    private lateinit var imageHolder: BaseImageView
    private var dataObserver = Observer<ImageData> {
        it?.let {
            updateUI(it)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        imageHolder = findViewById(R.id.main_image)
    }


    override fun onResume() {
        super.onResume()
        ImageInReview.observe(this, dataObserver)
    }

    override fun onPause() {
        super.onPause()
        ImageInReview.removeObserver(dataObserver)
    }


    private fun updateUI(data: ImageData) {

        imageHolder.imageSource(data.mainUrl, true)
        image_description.text = data.title
    }
}