package com.febinrukfan.newsfeed.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.febinrukfan.newsfeed.databinding.ActivityNewsFeedBinding
import com.febinrukfan.newsfeed.repository.NewsFeedRepository
import com.febinrukfan.newsfeed.ui.adapter.NewsFeedAdapter
import com.febinrukfan.newsfeed.utils.Resource

class NewsFeedActivity : AppCompatActivity() {

    lateinit var viewModel: NewsFeedViewModel

    lateinit var newsFeedAdapter: NewsFeedAdapter

    val TAG = this.javaClass.simpleName

    private lateinit var binding: ActivityNewsFeedBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val newsFeedRepository = NewsFeedRepository()
        val viewModelProviderFactory = NewsFeedViewModelProviderFactory(application,newsFeedRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsFeedViewModel::class.java)

        setupRecyclerView()



        viewModel.newsFeed.observe(this, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()

                    response.data?.let { newsResponse ->

                        newsFeedAdapter.differ.submitList(newsResponse)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }

                else -> {}
            }
        })    }

    private fun setupRecyclerView() {
        newsFeedAdapter = NewsFeedAdapter()
        binding.rvNewFeed.apply {
            adapter = newsFeedAdapter
            layoutManager = LinearLayoutManager(this@NewsFeedActivity)
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }
}








