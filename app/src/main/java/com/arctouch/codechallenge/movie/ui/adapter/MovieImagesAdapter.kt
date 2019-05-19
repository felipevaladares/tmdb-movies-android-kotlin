package com.arctouch.codechallenge.movie.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class MovieImagesAdapter(private val images: List<String>): RecyclerView.Adapter<MovieImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieImageViewHolder {
        return MovieImageViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MovieImageViewHolder, position: Int){
        val image = images[position]
        holder.bind(image)
    }

    override fun getItemCount() = images.size
}
