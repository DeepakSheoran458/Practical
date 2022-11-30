package com.demo.practical

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.demo.practical.adapter.MoviesAdapter
import com.demo.practical.databinding.ActivityMainBinding
import com.demo.practical.model.MoviesList
import com.demo.practical.network.ApiInterface
import com.demo.practical.network.Responce
import com.demo.practical.repo.MoviesRepo
import com.demo.practical.viewModel.MoviesViewModel
import com.demo.practical.viewModelFactory.MoviesViewModelFac
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding
    private lateinit var mApi : ApiInterface
    private lateinit var mViewModel : MoviesViewModel
    private lateinit var mRepo : MoviesRepo
    private lateinit var mList: MutableList<MoviesList>
    private lateinit var mAdapter : MoviesAdapter
    private var selectedYear: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

         mApi = ApiInterface.create()
         mRepo = MoviesRepo(mApi , this)
         mViewModel = ViewModelProvider(this , MoviesViewModelFac(mRepo)).get(MoviesViewModel::class.java)


        mViewModel.getMovie()
        observeMoviesList()

        mBinding.yearPicker.setOnClickListener {
            setupNumberPicker()
        }
    }

    private fun setupNumberPicker() {
        val numberPicker = mBinding.yearPicker
        numberPicker.minValue = 1960
        numberPicker.maxValue = 2022
        numberPicker.wrapSelectorWheel = true
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
           // val text = "Changed from $oldVal to $newVal"
             selectedYear = newVal.toString()
           // Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
            getMovieList(selectedYear)
        }
    }

    private fun getMovieList(selectedYear: String) {
        val tempList = mutableListOf<MoviesList>()
        mList.forEach{
            if(it.year == selectedYear)
                tempList.add(it)
        }
        mAdapter = MoviesAdapter(tempList , this)
        mBinding.recView.adapter = mAdapter
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