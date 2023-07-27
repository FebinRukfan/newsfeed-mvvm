package com.febinrukfan.newsfeed.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.febinrukfan.newsfeed.databinding.NewsFeedResponseItemBinding
import com.febinrukfan.newsfeed.models.NewsFeedResponseItem

class NewsFeedAdapter : RecyclerView.Adapter<NewsFeedAdapter.NewsFeedResponseItemViewHolder>() {

    val TAG = this.javaClass.simpleName


    inner class NewsFeedResponseItemViewHolder(val binding: NewsFeedResponseItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(get: NewsFeedResponseItem) {

            Glide.with(binding.root).load(get.typeAttributes.imageLarge).into(binding.ivNewsFeedImage)
            binding.tvTitle.text = get.title
            binding.tvPublishedAt.text = get.readablePublishedAt

        }
    }




    private val differCallback = object : DiffUtil.ItemCallback<NewsFeedResponseItem>() {
        override fun areItemsTheSame(oldItem: NewsFeedResponseItem, newItem: NewsFeedResponseItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: NewsFeedResponseItem, newItem: NewsFeedResponseItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedResponseItemViewHolder {
        val binding = NewsFeedResponseItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsFeedResponseItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        Log.v(TAG,"my_size" + differ.currentList.size.toString())
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsFeedResponseItemViewHolder, position: Int) {
        holder.bind(differ.currentList.get(position))
    }

}