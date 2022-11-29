package com.demo.practical.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.practical.model.MoviesList
import com.demo.practical.model.MoviesModel
import com.demo.practical.network.Responce
import com.demo.practical.repo.MoviesRepo
import kotlinx.coroutines.launch

class MoviesViewModel(val mRepo: MoviesRepo) : ViewModel() {

    val movieList : LiveData<Responce<MoviesModel>>
        get() = mRepo.movies

    fun getMovie(){
        viewModelScope.launch{
            mRepo.getMovies()
        }
    }

}