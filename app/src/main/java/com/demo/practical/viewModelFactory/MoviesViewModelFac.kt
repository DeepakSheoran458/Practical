package com.demo.practical.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.practical.repo.MoviesRepo
import com.demo.practical.viewModel.MoviesViewModel

class MoviesViewModelFac (private val mRepo: MoviesRepo) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(mRepo) as T
    }

}