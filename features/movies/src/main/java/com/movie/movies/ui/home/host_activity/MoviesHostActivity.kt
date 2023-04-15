package com.movie.movies.ui.home.host_activity

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.movie.core.base.BaseActivity
import com.movie.movies.R
import com.movie.movies.databinding.ActivityMoviesHostBinding
import com.movie.movies.ui.NowPlayingListFragment
import com.movie.movies.ui.home.search_fragment.SearchFragment
import com.movie.movies.ui.TopRatedListFragment
import com.movie.movies.ui.MyFavouriteFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviesHostActivity : BaseActivity<ActivityMoviesHostBinding, MovieHostViewModel>() {

    override fun initBinding(): ActivityMoviesHostBinding {
        return ActivityMoviesHostBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[MovieHostViewModel::class.java]
    }

    override fun onActivityCreated() {

        initBottomNavigation()

    }

    private fun initBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener{destination->
            when (destination.itemId) {
                R.id.nowPlayingFragment -> {
                    binding.topAppBar.title =
                        getString( R.string.now_playing)
                    setCurrentFragment(NowPlayingListFragment())
                }
                R.id.topRatedFragment -> {
                    binding.topAppBar.title =
                        getString(R.string.top_rated)
                    setCurrentFragment(TopRatedListFragment())
                }
                R.id.searchFragment -> {
                    binding.topAppBar.title =
                        getString(R.string.search)
                    setCurrentFragment(SearchFragment())
                }
            R.id.myFavouriteFragment -> {
                    binding.topAppBar.title =
                        getString(R.string.favourites)
                    setCurrentFragment(MyFavouriteFragment())
                }
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_fragment,fragment)
            commit()
        }

}