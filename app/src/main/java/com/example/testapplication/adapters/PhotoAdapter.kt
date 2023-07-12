package com.example.testapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapplication.R
import com.example.testapplication.models.Photo

class PhotoAdapter(private val photoList: List<Photo>): RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.iv_item_photo_image)
        val title: TextView = view.findViewById(R.id.tv_item_photo_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photoList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPhoto = photoList[position]
        holder.title.text = dataPhoto.title

        Glide.with(holder.itemView).load(dataPhoto.thumbnailUrl).into(holder.image)
    }

}