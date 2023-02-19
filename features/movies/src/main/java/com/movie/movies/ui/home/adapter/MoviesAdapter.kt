package com.movie.movies.ui.home.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.movie.movies.R
import com.movie.movies.databinding.ItemMovieBinding
import com.movie.movies.model.MovieModel
import com.movie.network.network.Endpoints.BASE_IMAGE_URL_API

class MoviesAdapter(
    var onItemClickListener: ((MovieModel) -> Unit),

    ) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var list: MutableList<MovieModel> = mutableListOf()

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieModel: MovieModel) {
            binding.tvMovieTitle.text = movieModel.title
            binding.tvVoteAverage.text = movieModel.voteAverage.toString()
            Glide.with(binding.root)
                .load(BASE_IMAGE_URL_API.plus(movieModel.backdropPath?.substring(1)))
                .placeholder(R.drawable.ic_movie_placeholder)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?,
                    ) {
                        binding.ivMoviePoster.setImageDrawable(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })

            binding.root.setOnClickListener {
                onItemClickListener.invoke(
                    movieModel,
                )
            }
        }

    }

    fun setAdapter(list: List<MovieModel>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun clearAdapter() {
        if (list.isNotEmpty())
            this.list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

