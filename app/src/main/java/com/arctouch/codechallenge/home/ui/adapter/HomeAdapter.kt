package com.arctouch.codechallenge.home.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.home.domain.model.Movie
import org.jetbrains.anko.sdk27.listeners.onClick

class HomeAdapter(
        private val movies: List<Movie>,
        private val listener: HomeAdapterListener) : RecyclerView.Adapter<HomeMovieViewHolder>() {

    interface HomeAdapterListener {
        fun onMovieClick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return HomeMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeMovieViewHolder, position: Int){
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.onClick {
            listener.onMovieClick(movie)
        }
    }

    override fun getItemCount() = movies.size
}
