package com.example.movizapp.repository

import com.example.movizapp.models.entities.RandomMovies
import com.example.movizapp.models.entities.RandomReviews
import com.example.movizapp.models.network.MovieApi
import com.example.movizapp.utils.Constants
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApi: MovieApi) {

    fun getUpcomingMovies(page:Int): Observable<RandomMovies.Movies> {
        return movieApi.getUpcomingMovies(
            Constants.API_KEY_VALUE,
            Constants.LANGUAGE_VALUE,
            page
        )
    }

    fun getPopularMovies(page:Int): Observable<RandomMovies.Movies> {
        return movieApi.getPopularMovies(
            Constants.API_KEY_VALUE,
            Constants.LANGUAGE_VALUE,
            page
        )
    }
    fun getTopRatedMovies(page:Int): Observable<RandomMovies.Movies> {
        return movieApi.getTopRatedMovies(
            Constants.API_KEY_VALUE,
            Constants.LANGUAGE_VALUE,
            page
        )
    }
    fun getReviews(movie_id:Int): Observable<RandomReviews.Reviews> {
        return movieApi.getReviews(
            movie_id,
            Constants.API_KEY_VALUE
        )
    }
    fun getSimilarMovies(movie_id: Int): Observable<RandomMovies.Movies> {
        return movieApi.getSimilarMovies(
            movie_id,
            Constants.API_KEY_VALUE,
        )
    }
}