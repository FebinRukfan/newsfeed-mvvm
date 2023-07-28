package com.febinrukfan.newsfeed.ui.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.febinrukfan.newsfeed.databinding.NewFeedTypeItemBinding
import com.febinrukfan.newsfeed.models.NewsFeedResponseItem

class NewsFeedTypeAdapter(private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<NewsFeedTypeAdapter.NewsFeedTypeItemViewHolder>() {

    val TAG = this.javaClass.simpleName


    inner class NewsFeedTypeItemViewHolder(val binding: NewFeedTypeItemBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(get: NewsFeedResponseItem) {

                binding.tvType.text = get.type

                binding.tvType.setOnClickListener {
                    itemClickListener.onItemClick(get.type)
                }


        }
    }


    private val differCallback = object : DiffUtil.ItemCallback<NewsFeedResponseItem>() {
        override fun areItemsTheSame(oldItem: NewsFeedResponseItem, newItem: NewsFeedResponseItem): Boolean {
            return oldItem.typeAttributes.imageLarge == newItem.typeAttributes.imageLarge
        }

        override fun areContentsTheSame(oldItem: NewsFeedResponseItem, newItem: NewsFeedResponseItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedTypeAdapter.NewsFeedTypeItemViewHolder {

        val binding = NewFeedTypeItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsFeedTypeItemViewHolder(binding)
    }

    override fun getItemCount(): Int {

        // removing "type" value duplicates by disctinct by
        return  (differ.currentList).distinctBy { it.type }.size
    }

    override fun onBindViewHolder(holder: NewsFeedTypeItemViewHolder, position: Int) {

        // removing "type" value duplicates by disctinct by
        holder.bind(differ.currentList.distinctBy { it.type }.get(position))
    }

    interface OnItemClickListener {
        fun onItemClick(value: String)
    }
}