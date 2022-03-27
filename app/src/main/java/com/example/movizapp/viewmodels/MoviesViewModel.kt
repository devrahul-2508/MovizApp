package com.example.movizapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movizapp.models.entities.RandomMovies
import com.example.movizapp.models.entities.RandomReviews
import com.example.movizapp.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviesViewModel(val repository: MovieRepository):ViewModel() {
   // private val movieService=MovieService()

    private val compositeDisposable=CompositeDisposable()
    val movieResponse=MutableLiveData<RandomMovies.Movies>()

    val reviewsResponse=MutableLiveData<RandomReviews.Reviews>()

    fun getTopRatedMoviesFromApi(page:Int){
        compositeDisposable.add(
            repository.getTopRatedMovies(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({response->
                    getObserver(response as RandomMovies.Movies)
                },
                    {t->onFailure(t)}
                )
        )
    }

    fun getPopularMoviesFromApi(page: Int){
        compositeDisposable.add(
             repository.getPopularMovies(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({response->
                    getObserver(response as RandomMovies.Movies)
                },
                    {t->onFailure(t)}
                )
        )
    }

    fun getUpcomingMoviesFromApi(page: Int){
        compositeDisposable.add(
            repository.getUpcomingMovies(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({response->
                    getObserver(response as RandomMovies.Movies)
                },
                    {t->onFailure(t)}
                )
        )
    }
    fun getMovieReviews(movie_id:Int){
        compositeDisposable.add(
            repository.getReviews(movie_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    response->
                        getReviewsObserver(response as RandomReviews.Reviews)
                },
                    {t->onFailure(t)}

                )
        )
    }
    fun getSimilarMovies(movie_id: Int){
        compositeDisposable.add(
            repository.getSimilarMovies(movie_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                        response->
                    getObserver(response as RandomMovies.Movies)
                },
                    {t->onFailure(t)}
                )
        )
    }

    private fun getReviewsObserver(reviews: RandomReviews.Reviews) {
        reviewsResponse.value=reviews
    }

    private fun getObserver(movies: RandomMovies.Movies) {
        movieResponse.value=movies
    }
    private fun onFailure(t: Throwable) {
        Log.d("main", "onFailure: "+t.message)

    }

}