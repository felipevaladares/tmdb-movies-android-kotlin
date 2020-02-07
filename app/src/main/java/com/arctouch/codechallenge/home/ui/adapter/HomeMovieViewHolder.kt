package com.arctouch.codechallenge.home.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.core.extensions.buildPosterUrl
import com.arctouch.codechallenge.core.extensions.inflate
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*

class HomeMovieViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.movie_item)) {
    fun bind(movie: Movie) {
        itemView.textViewTitle.text = movie.title
        itemView.textViewGenres.text = movie.genres?.joinToString(separator = ", ") { it.name }

        Glide.with(itemView)
                .load(movie.buildPosterUrl())
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.imageViewPoster)
    }
}