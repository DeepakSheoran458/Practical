package com.demo.practical.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.demo.practical.model.MoviesModel
import com.demo.practical.network.ApiInterface
import com.demo.practical.network.Responce

class MoviesRepo(val mApi : ApiInterface , val mContext: Context) {

    val movies = MutableLiveData<Responce<MoviesModel>>()

    suspend fun getMovies() {
        val result = mApi.getMovies()
        if (result.body() != null) {
            movies.postValue(Responce.Success(result.body()!!))
        } else {
            movies.postValue(
                Responce.Error(
                    result.body()!!.message
                )
            )
        }

    }

}