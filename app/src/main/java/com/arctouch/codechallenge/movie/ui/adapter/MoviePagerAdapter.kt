package com.arctouch.codechallenge.movie.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.movie.ui.MovieFragment
import com.arctouch.codechallenge.movie.ui.MovieImagesFragment

class MoviePagerAdapter(
        private val movie: Movie,
        fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MovieFragment.newInstance(movie)
            1 -> MovieImagesFragment()
            else -> throw IllegalArgumentException("Wrong tabs count on MoviePagerAdapter")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        //TODO review tabs titles
        return when (position) {
            0 -> "Overview"
            1 -> "Images"
            else -> throw IllegalArgumentException("Wrong tabs count on MoviePagerAdapter")
        }
    }

    override fun getCount(): Int {
        return 2
    }
}