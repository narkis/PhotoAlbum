package com.narunas.photoalbumviewer

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ComponentCallbacks2
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.transition.Fade
import android.transition.Transition
import android.view.Window
import android.widget.Toast
import com.narunas.photoalbumviewer.ui.PagerUIFragment
import com.narunas.photoalbumviewer.ui.SingleContainerFragment
import com.narunas.photoalbumviewer.ui.animation.Bounce
import com.narunas.photoalbumviewer.viewmodel.CommonViewModel
import com.narunas.photoalbumviewer.viewmodel.CommonViewModel.Companion.ErrorData
import com.narunas.photoalbumviewer.viewmodel.UI_VERSION
import javax.inject.Inject
import kotlin.concurrent.thread



class MainActivity : AppCompatActivity(), ComponentCallbacks2 {

    private lateinit var commonModel: CommonViewModel
    private lateinit var app : PhotoAlbumApp

    @Inject lateinit var singleUIFragment: SingleContainerFragment
    @Inject lateinit var pagerUIFragment: PagerUIFragment


    private val errorObserver = Observer<String> {
        it?.let {
            handleError(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)


        app = application as PhotoAlbumApp
        app.getUiElements().inject(this)




        commonModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)

        /** fetching data **/
        if(savedInstanceState == null) {
            thread {
                commonModel.buildCatalog()
            }
        }

        ErrorData.observe(this, errorObserver)

    }

    override fun onResume() {
        super.onResume()

        when(commonModel.APP_UI) {

            UI_VERSION.single -> {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.ui_container, singleUIFragment)
                    .commit()

            }
            UI_VERSION.tabs -> {



            }
            UI_VERSION.pager -> {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.ui_container, pagerUIFragment)
                    .commit()

            } else -> {

                ErrorData.postValue(" Failed to build UI")
            }
        }

    }

    override fun onStop() {
        super.onStop()
        ErrorData.removeObserver(errorObserver)
    }


    private fun handleError(error: String ) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

}
