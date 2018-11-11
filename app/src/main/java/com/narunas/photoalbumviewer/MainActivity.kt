package com.narunas.photoalbumviewer

import android.arch.lifecycle.ViewModelProviders
import android.content.ComponentCallbacks2
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.narunas.photoalbumviewer.ui.PagerUIFragment
import com.narunas.photoalbumviewer.ui.SingleContainerFragment
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

    /** may be good to have **/
    override fun onTrimMemory(level: Int) {

        when (level) {

            ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN -> {
                /*
                   Release any UI objects that currently hold memory.

                   The user interface has moved to the background.
                */
            }

            ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE,
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW,
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL -> {


                /*
                   Release any memory that your app doesn't need to run.

                   The device is running low on memory while the app is running.
                   The event raised indicates the severity of the memory-related event.
                   If the event is TRIM_MEMORY_RUNNING_CRITICAL, then the system will
                   begin killing background processes.
                */
            }

            ComponentCallbacks2.TRIM_MEMORY_BACKGROUND,
            ComponentCallbacks2.TRIM_MEMORY_MODERATE,
            ComponentCallbacks2.TRIM_MEMORY_COMPLETE -> {
                /*
                   Release as much memory as the process can.

                   The app is on the LRU list and the system is running low on memory.
                   The event raised indicates where the app sits within the LRU list.
                   If the event is TRIM_MEMORY_COMPLETE, the process will be one of
                   the first to be terminated.
                */
            }

            else -> {
                /*
                  Release any non-critical data structures.

                  The app received an unrecognized memory level value
                  from the system. Treat this as a generic low-memory message.
                */
            }
        }
    }
}
