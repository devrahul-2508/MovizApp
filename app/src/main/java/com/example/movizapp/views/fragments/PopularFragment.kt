package com.example.movizapp.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movizapp.Application.BaseApplication
import com.example.movizapp.R
import com.example.movizapp.adapters.MovieAdapters
import com.example.movizapp.databinding.FragmentPopularBinding
import com.example.movizapp.models.entities.RandomMovies
import com.example.movizapp.viewmodels.MoviesViewModel
import com.example.movizapp.viewmodels.ViewModelFactory
import javax.inject.Inject


class PopularFragment : Fragment() {


    private lateinit var moviesViewModel: MoviesViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: FragmentPopularBinding
    private lateinit var adapters: MovieAdapters
    private val movieList: ArrayList<RandomMovies.Result> = ArrayList()

    var currentPage: Int = 1
    var totalAvailablePages: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPopularBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        doInitialization()

        fetchPopularMovies()

        binding.pbLoading.visibility = View.GONE
        //paging
        binding.rvMovieList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1
                        fetchPopularMovies()
                    }
                }
            }


        })
    }

    private fun doInitialization() {
        val application=activity?.application
        (application as BaseApplication).applicationComponent.injectPopular(this)
        moviesViewModel = ViewModelProvider(this,viewModelFactory).get(MoviesViewModel::class.java)
        adapters = MovieAdapters(this.requireContext(),movieList)
        binding.rvMovieList.layoutManager = GridLayoutManager(this.requireContext(), 3)
        binding.rvMovieList.adapter = adapters
    }

    private fun fetchPopularMovies() {
        moviesViewModel.getPopularMoviesFromApi(currentPage)

            moviesViewModel.movieResponse.observe(viewLifecycleOwner) { movies ->
                movies.let {
                    totalAvailablePages = it.total_pages
                    val oldCount = movieList.size
                    movieList.addAll(it.results)
                    adapters.notifyItemRangeInserted(oldCount, movieList.size)

                }
            }
        }
    }





