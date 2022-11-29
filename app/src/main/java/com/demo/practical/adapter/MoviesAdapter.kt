package com.demo.practical.adapter

import android.content.Context
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.practical.MainActivity
import com.demo.practical.databinding.RecylerSingleRowLayoutBinding
import com.demo.practical.model.MoviesList
import com.squareup.picasso.Picasso

class MoviesAdapter(val mList : MutableList<MoviesList>,private val mContext : Context):RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){

    inner class ViewHolder(val mBinding :RecylerSingleRowLayoutBinding ):RecyclerView.ViewHolder(mBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bsBinding = RecylerSingleRowLayoutBinding.inflate(LayoutInflater.from(mContext) , parent , false)
        return ViewHolder(bsBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data  = mList.get(position)
        holder.mBinding.movieNameTv.text = data.name
        holder.mBinding.publishYearTv.text = data.year
        holder.mBinding.movieDirectorTv.text = data.director

        Picasso.get()
            .load(data.thumbnail).into(holder.mBinding.movieIv);

        holder.mBinding.remove.setOnClickListener{
            (mContext as MainActivity).deleteMovie(position)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

}