package com.example.movizapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movizapp.databinding.ItemMovieBinding
import com.example.movizapp.models.entities.RandomMovies
import com.example.movizapp.utils.Constants
import com.example.movizapp.views.activities.MovieDetailsActivity

class MovieAdapters(private val context:Context,private val movieList: List<RandomMovies.Result>): RecyclerView.Adapter<MovieAdapters.ViewHolder>() {


    class ViewHolder(itemView:ItemMovieBinding):RecyclerView.ViewHolder(itemView.root){

        val title=itemView.movieTitle
        val image=itemView.movieImage

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ItemMovieBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=movieList[position]
        holder.title.text=item.title
        Glide.with(context).load(Constants.IMAGE_PATH+item.poster_path).into(holder.image)

        holder.itemView.setOnClickListener {
            val intent= Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("id",item.id)
            intent.putExtra("title",item.title)
            intent.putExtra("language",item.original_language)
            intent.putExtra("popularity",item.popularity.toString())
            intent.putExtra("releaseDate",item.release_date)
            intent.putExtra("description",item.overview)
            intent.putExtra("rating",item.vote_count.toString())
            intent.putExtra("posterPath",item.poster_path)
            intent.putExtra("backdropPath",item.backdrop_path)
            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }


}