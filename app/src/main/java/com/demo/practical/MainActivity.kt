package com.demo.practical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.demo.practical.adapter.MoviesAdapter
import com.demo.practical.databinding.ActivityMainBinding
import com.demo.practical.model.MoviesList
import com.demo.practical.network.ApiInterface
import com.demo.practical.network.Responce
import com.demo.practical.repo.MoviesRepo
import com.demo.practical.viewModel.MoviesViewModel
import com.demo.practical.viewModelFactory.MoviesViewModelFac

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding
    private lateinit var mApi : ApiInterface
    private lateinit var mViewModel : MoviesViewModel
    private lateinit var mRepo : MoviesRepo
    private lateinit var mList: MutableList<MoviesList>
    private lateinit var mAdapter : MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

         mApi = ApiInterface.create()
         mRepo = MoviesRepo(mApi , this)
         mViewModel = ViewModelProvider(this , MoviesViewModelFac(mRepo)).get(MoviesViewModel::class.java)


        mViewModel.getMovie()
        observeMoviesList()


    }

    private fun observeMoviesList() {
        mViewModel.movieList.observe(this){
            when (it) {
                is Responce.Loading -> {
                }
                is Responce.Success -> {
                    mList = it.data?.data?.movies!!
                    mList.sortWith{
                        lhs, rhs -> lhs.year.compareTo(rhs.year)
                    }
                    mList.reverse()
                    mAdapter = MoviesAdapter(mList , this)
                    mBinding.recView.adapter = mAdapter
                }
                is Responce.Error -> {
                }
                is Responce.NetworkError -> {
                }
            }
        }
    }

    fun deleteMovie(position: Int) {
        mList.removeAt(position)
        mAdapter.notifyItemRemoved(position)
    }
}