package com.arctouch.codechallenge.home.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.arctouch.codechallenge.core.domain.model.Movie
import org.jetbrains.anko.sdk27.listeners.onClick

class HomeAdapter(
        private val movies: MutableList<Movie>,
        private val listener: HomeAdapterListener) : RecyclerView.Adapter<HomeMovieViewHolder>() {

    interface HomeAdapterListener {
        fun onMovieClick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMovieViewHolder {
        return HomeMovieViewHolder(parent)
    }

    override fun onBindViewHolder(holder: HomeMovieViewHolder, position: Int){
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.onClick {
            listener.onMovieClick(movie)
        }
    }

    override fun getItemCount() = movies.size

    fun insertItems(movies: List<Movie>) {
        val startPosition = itemCount - 1
        this.movies.addAll(movies)
        notifyItemRangeInserted(startPosition, movies.size)
    }
}
