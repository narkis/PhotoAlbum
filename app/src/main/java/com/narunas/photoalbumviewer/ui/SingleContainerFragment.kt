package com.narunas.photoalbumviewer.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.narunas.photoalbumviewer.R
import com.narunas.photoalbumviewer.gson.ImageData
import com.narunas.photoalbumviewer.ui.adapters.SingleFragmentAdapter
import com.narunas.photoalbumviewer.viewmodel.CommonViewModel.Companion.AlbumData

class SingleContainerFragment: Fragment() {

    companion object {
        val TAG: String = SingleContainerFragment::class.java.simpleName

        fun getInstance() : SingleContainerFragment {
            return SingleContainerFragment()
        }
    }

    private lateinit var mapAdapter : SingleFragmentAdapter
    private lateinit var lm: LinearLayoutManager
    private lateinit var singleRecycler: RecyclerView
    private var dataObserver = Observer<HashMap<Int, ArrayList<ImageData>>> {
        it?.let {

            updateUI(it)

        }
    }

    override fun onStart() {
        super.onStart()

        AlbumData.observe(this, dataObserver)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()
        AlbumData.removeObserver(dataObserver)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.single_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        singleRecycler = view.findViewById(R.id.single_recycler)

        lm =  LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        singleRecycler.layoutManager = lm

        mapAdapter = SingleFragmentAdapter()
        singleRecycler.adapter = mapAdapter

    }


    private fun updateUI(data: HashMap<Int, ArrayList<ImageData>>) {

        mapAdapter.updateData(data)

    }
}