package com.example.movizapp.views.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movizapp.Application.BaseApplication
import com.example.movizapp.R
import com.example.movizapp.adapters.MovieAdapters
import com.example.movizapp.adapters.ReviewsAdapter
import com.example.movizapp.databinding.ActivityMovieDetailsBinding
import com.example.movizapp.models.entities.RandomMovies
import com.example.movizapp.models.entities.RandomReviews
import com.example.movizapp.utils.Constants
import com.example.movizapp.viewmodels.MoviesViewModel
import com.example.movizapp.viewmodels.ViewModelFactory
import javax.inject.Inject

class MovieDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMovieDetailsBinding
    lateinit var moviesViewModel: MoviesViewModel
    lateinit var reviewsAdapter: ReviewsAdapter
    lateinit var moviesAdapters: MovieAdapters
    private val similarMovieList: ArrayList<RandomMovies.Result> = ArrayList()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        doInitialization();


        val movieId = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("title")
        val language = intent.getStringExtra("language")
        val popularity = intent.getStringExtra("popularity")
        val releaseDate = intent.getStringExtra("releaseDate")
        val description = intent.getStringExtra("description")
        val rating = intent.getStringExtra("rating")
        val posterPath = intent.getStringExtra("posterPath")
        val backdropPath = intent.getStringExtra("backdropPath")



        Log.i("movieid", "$movieId")

        binding.textName.text = title
        binding.textLanguage.text = "Language: $language"
        binding.textPopularity.text = "Popularity: $popularity"
        binding.textStartedDate.text = "Released on: $releaseDate"
        binding.textDescription.text = description
        Glide.with(this).load(Constants.IMAGE_PATH + posterPath).into(binding.imageTvShow)
        Glide.with(this).load(Constants.IMAGE_PATH + backdropPath).into(binding.sliderViewPager)


        getSimilarMovies(movieId)
        getReviews(movieId)


    }

    private fun getSimilarMovies(movieId: Int) {
        moviesViewModel.getSimilarMovies(movieId)
        moviesViewModel.movieResponse.observe(this) { movies ->
            Log.i("Similar Movies", "${movies.results}")
            similarMovieList.addAll(movies.results)
            moviesAdapters.notifyDataSetChanged()
        }
    }

    private fun getReviews(movieId: Int) {
        moviesViewModel.getMovieReviews(movieId)
        moviesViewModel.reviewsResponse.observe(this) { reviews ->
            if (reviews.results.isNullOrEmpty()) {
                binding.noReviewsTV.visibility = View.VISIBLE
                binding.idRVReviews.visibility = View.GONE
            } else {
                binding.noReviewsTV.visibility = View.GONE
                binding.idRVReviews.visibility = View.VISIBLE
                reviewsAdapter.reviews(reviews.results)
                reviewsAdapter.notifyDataSetChanged()
                Log.i("Reviews", "${reviews.results}")
            }


        }
    }


    private fun doInitialization() {
        //initializing viewmodel
        (application as BaseApplication).applicationComponent.injectMovieDetailsActivity(this)
        moviesViewModel = ViewModelProvider(this,viewModelFactory).get(MoviesViewModel::class.java)
        //initializing reviewsAdapter
        reviewsAdapter = ReviewsAdapter(this)
        binding.idRVReviews.layoutManager = LinearLayoutManager(this)
        binding.idRVReviews.adapter = reviewsAdapter
        //initializing moviesAdapters
        moviesAdapters = MovieAdapters(this, similarMovieList)
        binding.idRVSimilarMovies.layoutManager = GridLayoutManager(this, 2)
        binding.idRVSimilarMovies.adapter = moviesAdapters
    }
}