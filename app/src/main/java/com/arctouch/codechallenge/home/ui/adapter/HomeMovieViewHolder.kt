package com.arctouch.codechallenge.home.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.di.GlideApp
import com.arctouch.codechallenge.core.extensions.buildPosterUrl
import com.arctouch.codechallenge.core.extensions.inflate
import com.arctouch.codechallenge.home.domain.model.Movie
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*

class HomeMovieViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.movie_item)) {
    fun bind(movie: Movie) {
        itemView.textViewTitle.text = movie.title
        itemView.textViewGenres.text = movie.genres?.joinToString(separator = ", ") { it.name }

        GlideApp.with(itemView)
                .load(movie.buildPosterUrl())
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.imageViewPoster)
    }
}