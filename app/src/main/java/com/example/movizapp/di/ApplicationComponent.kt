package com.example.movizapp.di

import android.app.Application
import com.example.movizapp.views.activities.MainActivity
import com.example.movizapp.views.activities.MovieDetailsActivity
import com.example.movizapp.views.fragments.PopularFragment
import com.example.movizapp.views.fragments.TopRatedFragment
import com.example.movizapp.views.fragments.UpcomingFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun injectUpcoming(upcomingFragment: UpcomingFragment)

    fun injectPopular(popularFragment: PopularFragment)

    fun injectTopRated(topRatedFragment: TopRatedFragment)

    fun injectMovieDetailsActivity(movieDetailsActivity: MovieDetailsActivity)
}