package com.narunas.photoalbumviewer.ui.adapters

import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.GridLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import com.narunas.photoalbumviewer.DetailsActivity
import com.narunas.photoalbumviewer.MainActivity
import com.narunas.photoalbumviewer.PagerActivity
import com.narunas.photoalbumviewer.PagerActivity.Companion.ACT_IMG
import com.narunas.photoalbumviewer.R
import com.narunas.photoalbumviewer.gson.ImageData
import com.narunas.photoalbumviewer.ui.common.BaseImageView
import com.narunas.photoalbumviewer.viewmodel.CommonViewModel.Companion.APP_UI
import com.narunas.photoalbumviewer.viewmodel.CommonViewModel.Companion.AlbumInReview
import com.narunas.photoalbumviewer.viewmodel.CommonViewModel.Companion.ErrorData
import com.narunas.photoalbumviewer.viewmodel.CommonViewModel.Companion.ImageInReview
import com.narunas.photoalbumviewer.viewmodel.UI_VERSION
import java.lang.StringBuilder

class SingleFragmentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var dataMap =  HashMap<Int, ArrayList<ImageData>>()


    fun updateData(data:  HashMap<Int, ArrayList<ImageData>>) {

        dataMap = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_album, viewGroup, false)
        return SingleAlbumViewHolder(v)
    }

    override fun getItemCount(): Int {
       return dataMap.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder is SingleAlbumViewHolder) {

            /** albums are not zero index, adapter data is **/
            val albumKey = position + 1
            val imageData = dataMap.get(albumKey)

            val sb = StringBuilder()
            sb.append(holder.itemView.resources.getString(R.string.album)).append(albumKey)
            holder.albumIndex.text = sb.toString()

            val gridAdapter = GridAdapter()
            gridAdapter.updateData(imageData!!)

            val gridLm = GridLayoutManager(
                holder.itemView.context,
                holder.itemView.resources.getInteger(R.integer.column_count),
                GridLayoutManager.VERTICAL,
                false)

            holder.grid.layoutManager = gridLm
            holder.grid.adapter = gridAdapter

        }

    }

    inner class SingleAlbumViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var grid: RecyclerView
        var albumIndex: TextView

        init {

            grid = itemView.findViewById(R.id.image_grid)
            albumIndex = itemView.findViewById(R.id.album_index)
        }


    }

    class GridAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var dataSet = ArrayList<ImageData>()

        fun updateData(data: ArrayList<ImageData>) {
            dataSet = data
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(view: ViewGroup, p1: Int): RecyclerView.ViewHolder {
            val v = LayoutInflater.from(view.context).inflate(R.layout.card_image, view, false)
            return ImageViewHolder(v)
        }

        override fun getItemCount(): Int {
            return dataSet.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            if(holder is ImageViewHolder) {

                holder.image.imageSource(dataSet[position].thumbUrl, true)

                holder.image.setOnClickListener {

                    /** switch for single / pager detail view **/
                    when(APP_UI) {
                        UI_VERSION.single -> {

                            ImageInReview.postValue(dataSet[position])
                            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
                            holder.itemView.context.startActivity(intent)

                        }
                        UI_VERSION.pager -> {

                            AlbumInReview.postValue(dataSet)
                            val intent = Intent(holder.itemView.context, PagerActivity::class.java)
                            intent.putExtra(ACT_IMG, position)
                            holder.itemView.context.startActivity(intent)

                        } else -> {

                            ErrorData.postValue(" Error viewing content ")
                        }

                    }
                }
            }
        }

        inner class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            var image: BaseImageView

            init {
                image = itemView.findViewById(R.id.card_image_holder)
            }

        }
    }
}