package com.example.movizapp.views.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movizapp.Application.BaseApplication
import com.example.movizapp.R
import com.example.movizapp.adapters.MovieAdapters
import com.example.movizapp.databinding.ActivityMainBinding
import com.example.movizapp.models.entities.RandomMovies
import com.example.movizapp.viewmodels.MoviesViewModel
import com.example.movizapp.viewmodels.ViewModelFactory
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    private var doubleBackToExitPressedOnce = false
    lateinit var moviesViewModel: MoviesViewModel
    lateinit var moviesAdapters: MovieAdapters
    private val searchMovieResults: ArrayList<RandomMovies.Result> = ArrayList()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        doInitialization()

        navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)


        binding.idEdtSearch.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                hideFragmentViews()
            } else {
                showFragmentViews()
            }
        })
        binding.idEdtSearch.setOnClickListener {
            hideFragmentViews()
        }

        binding.idIVSearch.setOnClickListener {
            searchMovies()
        }
    }

    private fun searchMovies() {
        val query: String = binding.idEdtSearch.text.toString()

        moviesViewModel.getSearchResults(query)
        moviesViewModel.movieResponse.observe(this) {
                movies ->
            if(movies.results.isNullOrEmpty()){
                binding.idRVsearchResults.visibility=View.GONE
                binding.noResultsTV.visibility=View.VISIBLE
            }
            else{
                Log.i("SearchResults", "${movies.results}")
                binding.idRVsearchResults.visibility=View.VISIBLE
                binding.noResultsTV.visibility=View.GONE
                searchMovieResults.clear()
                searchMovieResults.addAll(movies.results)
                moviesAdapters.notifyDataSetChanged()
            }

        }
    }

    private fun doInitialization() {
        (application as BaseApplication).applicationComponent.injectMainActivity(this)
        moviesViewModel = ViewModelProvider(this, viewModelFactory).get(MoviesViewModel::class.java)

        moviesAdapters = MovieAdapters(this, searchMovieResults)
        binding.idRVsearchResults.layoutManager = GridLayoutManager(this, 2)
        binding.idRVsearchResults.adapter = moviesAdapters
    }

    private fun hideFragmentViews() {
        binding.containerFragments.visibility = View.GONE
        binding.searchResultsLayout.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun onBackPressed() {
        showFragmentViews();
        binding.idEdtSearch.text=null
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }


    private fun showFragmentViews() {
        binding.containerFragments.visibility = View.VISIBLE
        binding.searchResultsLayout.visibility = View.GONE

    }
}