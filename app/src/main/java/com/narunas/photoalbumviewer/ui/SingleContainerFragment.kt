package com.narunas.photoalbumviewer.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.narunas.photoalbumviewer.R

class SingleContainerFragment: Fragment() {

    companion object {
        val TAG: String = SingleContainerFragment::class.java.simpleName

        fun getInstance() : SingleContainerFragment {
            return SingleContainerFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.single_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }
}