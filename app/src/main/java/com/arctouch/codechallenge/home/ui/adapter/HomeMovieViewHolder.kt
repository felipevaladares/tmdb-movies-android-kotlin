package com.arctouch.codechallenge.home.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.core.di.GlideApp
import com.arctouch.codechallenge.core.util.MovieImageUrlBuilder
import com.arctouch.codechallenge.home.domain.model.Movie
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*

class HomeMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val movieImageUrlBuilder = MovieImageUrlBuilder()

    fun bind(movie: Movie) {
        itemView.titleTextView.text = movie.title
        itemView.genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
        itemView.releaseDateTextView.text = movie.releaseDate

        GlideApp.with(itemView)
                .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.posterImageView)
    }
}