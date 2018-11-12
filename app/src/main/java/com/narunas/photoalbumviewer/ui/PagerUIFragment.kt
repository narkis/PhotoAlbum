package com.narunas.photoalbumviewer.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.narunas.photoalbumviewer.R
import com.narunas.photoalbumviewer.gson.ImageData
import kotlinx.android.synthetic.main.pager_fragment.*

class PagerUIFragment: Fragment() {
    companion object {

        private val IMG_P: String = "imageUrl"
        private val TITLE_P: String = "title"
        fun getInstance(image: ImageData) :PagerUIFragment {
            val f = PagerUIFragment()
            val bundle = Bundle()
            bundle.putString(IMG_P, image.mainUrl)
            bundle.putString(TITLE_P, image.title)
            f.arguments = bundle
            return f
        }
    }

    private var imageUrl: String? = null
    private var title: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        imageUrl = arguments?.getString(IMG_P)
        title = arguments?.getString(TITLE_P)

        return inflater.inflate(R.layout.pager_fragment, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(imageUrl != null) {

            frag_main_image.imageSource(imageUrl!!, true)
        }

        if(title != null) {

            frag_image_description.text = title
        }
    }
}