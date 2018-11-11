package com.narunas.photoalbumviewer

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.narunas.simpledetailtest.base.BaseApplicationTest
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class InstrumentTestsMain: BaseApplicationTest<MainActivity>(MainActivity::class.java) {

    lateinit var app : App

    @Before
    fun setUp() {

        app = ApplicationProvider.getApplicationContext()

    }


    @After
    fun destroy() {

    }


    @Test
    fun domainContext() {

        assertEquals(R.string.domain, app.packageName)
    }

}