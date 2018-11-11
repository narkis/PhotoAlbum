package com.narunas.photoalbumviewer

import android.arch.lifecycle.ViewModelProviders
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.logging.AtraceLogger
import com.narunas.photoalbumviewer.viewmodel.CommonViewModel
import com.narunas.simpledetailtest.base.BaseApplicationTest
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.concurrent.thread


@RunWith(AndroidJUnit4::class)
class InstrumentTestsMain: BaseApplicationTest<MainActivity>(MainActivity::class.java) {

    private val appContext = ApplicationProvider.getApplicationContext<PhotoAlbumApp>()
    lateinit var  mainActivity: MainActivity

    @Before
    fun setUp() {

        mainActivity = testRule.activity

    }


    @After
    fun destroy() {

    }


    @Test
    fun domainContext() {

        assertEquals(appContext.resources.getString(R.string.domain), appContext.packageName)
    }

    @Test
    fun checkHttpRequest() {

        val model = ViewModelProviders.of(mainActivity).get(CommonViewModel::class.java)

        thread {
            model.fetchJsonData()
        }

    }

    @Test
    fun checkReturnedDataParsing() {

        val model = ViewModelProviders.of(mainActivity).get(CommonViewModel::class.java)

        thread {
            model.buildCatalog()
        }

    }

}