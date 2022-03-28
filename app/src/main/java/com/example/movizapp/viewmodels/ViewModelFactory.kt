package com.example.movizapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movizapp.repository.MovieRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val movieRepository: MovieRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesViewModel(movieRepository) as T
    }
}