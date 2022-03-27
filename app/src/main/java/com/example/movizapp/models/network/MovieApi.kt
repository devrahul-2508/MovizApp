package com.example.movizapp.models.network

import com.example.movizapp.models.entities.RandomMovies
import com.example.movizapp.models.entities.RandomReviews
import com.example.movizapp.utils.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET(Constants.UPCOMING_END_POINT)
    fun getUpcomingMovies(
        @Query(Constants.API_KEY) api_key: String,
        @Query(Constants.LANGUAGE) language:String,
        @Query(Constants.PAGE) page:Int
    ):Observable<RandomMovies.Movies>

    @GET(Constants.POPULAR_END_POINT)
    fun getPopularMovies(
        @Query(Constants.API_KEY) api_key:String,
        @Query(Constants.LANGUAGE) language:String,
        @Query(Constants.PAGE) page:Int
    ):Observable<RandomMovies.Movies>

    @GET(Constants.TOP_RATED_END_POINT)
    fun getTopRatedMovies(
        @Query(Constants.API_KEY) api_key:String,
        @Query(Constants.LANGUAGE) language:String,
        @Query(Constants.PAGE) page:Int
    ):Observable<RandomMovies.Movies>

    @GET("/3/movie/{movie_id}/reviews")
    fun getReviews(
        @Path(Constants.MOVIE_ID) movie_id:Int,
        @Query(Constants.API_KEY) api_key:String
    ):Observable<RandomReviews.Reviews>

    @GET("/3/movie/{movie_id}/similar")
    fun getSimilarMovies(
        @Path(Constants.MOVIE_ID) movie_id:Int,
        @Query(Constants.API_KEY) api_key:String,
    ):Observable<RandomMovies.Movies>

}