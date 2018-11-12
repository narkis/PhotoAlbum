package com.narunas.photoalbumviewer

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.narunas.photoalbumviewer.gson.ImageData
import com.narunas.photoalbumviewer.ui.PagerUIFragment
import com.narunas.photoalbumviewer.viewmodel.CommonViewModel.Companion.AlbumInReview

class PagerActivity: AppCompatActivity()  {

    companion object {
        val ACT_IMG: String = "activeImage"
    }
    private var initialImageIndex: Int = -1
    private lateinit var pager: ViewPager
    private var dataObserver = Observer<ArrayList<ImageData>> {
        it?.let {
            updateUI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pager)

        initialImageIndex = intent.getIntExtra(ACT_IMG, 0)

        pager = findViewById(R.id.view_pager)



    }

    override fun onResume() {
        super.onResume()
        AlbumInReview.observe(this, dataObserver)
    }


    override fun onPause() {
        super.onPause()
        AlbumInReview.removeObserver(dataObserver)
    }


    private fun updateUI(data: ArrayList<ImageData>) {

        val imageAdapter = FragPagerAdapter(supportFragmentManager, data)
        pager.adapter = imageAdapter
        pager.currentItem = initialImageIndex


    }

    private inner class FragPagerAdapter(fm: FragmentManager, data: ArrayList<ImageData>) : FragmentStatePagerAdapter(fm) {

        private val dataSet = data

        override fun getItem(position: Int): Fragment {
            return PagerUIFragment.getInstance(dataSet[position])
        }

        override fun getCount(): Int {
            return dataSet.size
        }
    }

}