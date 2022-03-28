package com.example.movizapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movizapp.databinding.ItemReviewsBinding
import com.example.movizapp.models.entities.RandomReviews
import com.example.movizapp.utils.Constants

class ReviewsAdapter(private val context: Context):RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    private var reviewsList: List<RandomReviews.Result> = listOf()

    class ViewHolder(itemView:ItemReviewsBinding):RecyclerView.ViewHolder(itemView.root){

        val userName=itemView.textUserName
        val name=itemView.textName
        val content=itemView.textContent
        val rating=itemView.textRating
        val userImage=itemView.imageUser

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding=ItemReviewsBinding.inflate(LayoutInflater.from(context),parent,false)
       return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item=reviewsList[position]
    holder.userName.setText("@"+item.author_details.username)
    holder.name.text=item.author
    holder.content.text=item.content
        if(item.author_details.rating!=null){
            holder.rating.text=item.author_details.rating.toString()
        }   else
            holder.rating.text="no rating"
        Glide.with(context).load(Constants.IMAGE_PATH+item.author_details.avatar_path).into(holder.userImage)

    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }
    fun reviews(list: List<RandomReviews.Result>){
        reviewsList=list
    }
}