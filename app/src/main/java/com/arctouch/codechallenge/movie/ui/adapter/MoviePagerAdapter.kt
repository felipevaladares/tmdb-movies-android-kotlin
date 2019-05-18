package com.arctouch.codechallenge.movie.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.arctouch.codechallenge.home.domain.model.Movie
import com.arctouch.codechallenge.movie.ui.MovieFragment

class MoviePagerAdapter(
        private val movie: Movie,
        fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return MovieFragment.newInstance(movie)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Main"
    }

    override fun getCount(): Int {
        return 1
    }
}