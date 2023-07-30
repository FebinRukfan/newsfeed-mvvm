package com.febinrukfan.newsfeed.newsfeed.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.febinrukfan.newsfeed.R
import com.febinrukfan.newsfeed.databinding.NewsFeedResponseItemBinding
import com.febinrukfan.newsfeed.newsfeed.model.NewsFeedResponseItem

class NewsFeedAdapter(private var context: Context) : RecyclerView.Adapter<NewsFeedAdapter.NewsFeedResponseItemViewHolder>() {

    val TAG = this.javaClass.simpleName

    private var onItemClickListener: ((NewsFeedResponseItem) -> Unit)? = null



    inner class NewsFeedResponseItemViewHolder(val binding: NewsFeedResponseItemBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(get: NewsFeedResponseItem) {

            Glide.with(binding.root).load(get.typeAttributes.imageLarge).into(binding.ivNewsFeedImage)
            binding.tvTitle.text = get.title
            binding.tvPublishedAt.text = get.readablePublishedAt

            val sharedPreferences = context.getSharedPreferences("Saved_Ids", Context.MODE_PRIVATE)

            if(sharedPreferences.getInt("${get.id}",0) != get.id && get.idLocal == null){
                binding.ivSave.setImageResource(R.drawable.deleted)
            }else{
                binding.ivSave.setImageResource(R.drawable.saved)

            }

            binding.ivSave.setOnClickListener {

                onItemClickListener?.let { it(get) }



                if(sharedPreferences.getInt("${get.id}",0) != get.id && get.idLocal == null){

                    Log.e(TAG,"shared_pref"+ sharedPreferences.getInt("${get.id}",0))


                    binding.ivSave.setImageResource(R.drawable.deleted)

                    sharedPreferences.edit().apply(){
                        putInt("${get.id}",get.id)
                    }.apply()

                }else{
                    binding.ivSave.setImageResource(R.drawable.saved)

                    sharedPreferences.edit().remove("${get.id}").apply()

                }


            }

        }
    }

    fun setOnItemClickListener(listener: (NewsFeedResponseItem) -> Unit) {
        onItemClickListener = listener
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
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsFeedResponseItemViewHolder, position: Int) {
        holder.bind(differ.currentList.get(position))
    }


}