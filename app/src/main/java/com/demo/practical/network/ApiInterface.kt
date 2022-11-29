package com.demo.practical.network

import com.demo.practical.model.MoviesList
import com.demo.practical.model.MoviesModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiInterface {



    @GET("v1/movies")
    suspend fun getMovies(): Response<MoviesModel>


    companion object {

        private val baseUrl : String = "http://165.22.75.82/"

        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder().client(client())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)

                .build()
            return retrofit.create(ApiInterface::class.java)
        }

        private fun client(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


            return OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .followRedirects(false)
                .followSslRedirects(false)
                .addInterceptor(interceptor)
                .build()
        }
    }

}