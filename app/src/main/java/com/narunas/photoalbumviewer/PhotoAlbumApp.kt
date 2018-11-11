package com.narunas.photoalbumviewer

import android.app.Application
import com.narunas.photoalbumviewer.components.DaggerUIComponent
import com.narunas.photoalbumviewer.components.UIComponent
import com.narunas.photoalbumviewer.modules.PagerFragmentUIModule
import com.narunas.photoalbumviewer.modules.SingleFragmentUIModule

class PhotoAlbumApp :Application() {

    lateinit var uiComponent: UIComponent

    override fun onCreate() {
        super.onCreate()

        uiComponent = createUiComponent()

    }


    fun getUiElements(): UIComponent {
        return uiComponent
    }


    private fun createUiComponent(): UIComponent  =
        DaggerUIComponent.builder()
            .pagerFragmentUIModule(PagerFragmentUIModule())
            .singleFragmentUIModule(SingleFragmentUIModule())
            .build()

}