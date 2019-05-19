package com.arctouch.codechallenge.movie.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.di.GlideApp
import com.arctouch.codechallenge.core.extensions.inflate
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_image_item.view.*

class MovieImageViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.movie_image_item)) {
    fun bind(imagePath: String) {
        GlideApp.with(itemView)
                .load(imagePath)
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.imageViewMovie)
    }
}