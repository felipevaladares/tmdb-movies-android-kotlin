package com.arctouch.codechallenge.movie.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.movie.ui.MovieFragment
import com.arctouch.codechallenge.movie.ui.MovieImagesFragment

class MoviePagerAdapter(
        private val movie: Movie,
        fragmentManager: FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

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