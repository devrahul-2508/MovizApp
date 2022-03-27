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
import com.example.movizapp.databinding.FragmentTopRatedBinding
import com.example.movizapp.models.entities.RandomMovies
import com.example.movizapp.viewmodels.MoviesViewModel
import com.example.movizapp.viewmodels.ViewModelFactory
import javax.inject.Inject

class TopRatedFragment : Fragment() {


    lateinit var moviesViewModel: MoviesViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var binding:FragmentTopRatedBinding
    lateinit var adapters: MovieAdapters
    private val movieList: ArrayList<RandomMovies.Result> = ArrayList()

    var currentPage: Int = 1
    var totalAvailablePages: Int = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentTopRatedBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val application=activity?.application
        (application as BaseApplication).applicationComponent.injectTopRated(this)
        moviesViewModel=ViewModelProvider(this,viewModelFactory).get(MoviesViewModel::class.java)
        adapters= MovieAdapters(this.requireContext(),movieList)
        binding.rvMovieList.layoutManager= GridLayoutManager(this.requireContext(),2)
        binding.rvMovieList.adapter=adapters

        fetchTopRatedMovies()
        //paging
        binding.rvMovieList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!binding.rvMovieList.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1
                        fetchTopRatedMovies()
                    }
                }
            }
        })


    }

    private fun fetchTopRatedMovies() {
        moviesViewModel.getTopRatedMoviesFromApi(currentPage)

            moviesViewModel.movieResponse.observe(viewLifecycleOwner) { movies ->
                movies.let {
                    totalAvailablePages = it.total_pages
                    val oldCount = movieList.size
                    movieList.addAll(movies.results)
                    adapters.notifyItemRangeInserted(oldCount, movieList.size)

                }
            }
        }
    }


