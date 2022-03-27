package com.example.movizapp.models.network

import com.example.movizapp.models.entities.RandomMovies
import com.example.movizapp.models.entities.RandomReviews
import com.example.movizapp.utils.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieService {

    private val api=Retrofit
        .Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(MovieApi::class.java)

    fun getUpcomingMovies(page:Int):Observable<RandomMovies.Movies>{
        return api.getUpcomingMovies(
            Constants.API_KEY_VALUE,
            Constants.LANGUAGE_VALUE,
            page
        )
    }

    fun getPopularMovies(page:Int):Observable<RandomMovies.Movies>{
        return api.getPopularMovies(
            Constants.API_KEY_VALUE,
            Constants.LANGUAGE_VALUE,
            page
        )
    }
    fun getTopRatedMovies(page:Int):Observable<RandomMovies.Movies> {
        return api.getTopRatedMovies(
            Constants.API_KEY_VALUE,
            Constants.LANGUAGE_VALUE,
            page
        )
    }
    fun getReviews(movie_id:Int):Observable<RandomReviews.Reviews>{
        return api.getReviews(
            movie_id,
            Constants.API_KEY_VALUE
        )
    }
    fun getSimilarMovies(movie_id: Int):Observable<RandomMovies.Movies>{
        return api.getSimilarMovies(
            movie_id,
            Constants.API_KEY_VALUE,
        )
    }
}