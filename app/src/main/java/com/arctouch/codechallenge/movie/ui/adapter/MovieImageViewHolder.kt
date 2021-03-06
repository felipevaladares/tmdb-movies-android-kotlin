package com.arctouch.codechallenge.movie.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.domain.model.MovieImage
import com.arctouch.codechallenge.core.extensions.buildPosterUrl
import com.arctouch.codechallenge.core.extensions.inflate
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_image_item.view.*

class MovieImageViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.movie_image_item)) {
    fun bind(image: MovieImage) {
        Glide.with(itemView)
                .load(image.buildPosterUrl())
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.imageViewMovie)
    }
}