package com.febinrukfan.newsfeed.newsfeed.view

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.febinrukfan.newsfeed.databinding.ActivityNewsFeedBinding
import com.febinrukfan.newsfeed.newsfeed.model.local.db.NewsFeedDatabase
import com.febinrukfan.newsfeed.newsfeed.viewmodel.NewsFeedViewModel
import com.febinrukfan.newsfeed.newsfeed.viewmodel.NewsFeedViewModelProviderFactory
import com.febinrukfan.newsfeed.newsfeed.model.repository.NewsFeedRepository
import com.febinrukfan.newsfeed.newsfeed.view.adapter.NewsFeedAdapter
import com.febinrukfan.newsfeed.newsfeed.view.adapter.NewsFeedTypeAdapter
import com.febinrukfan.newsfeed.utils.NetworkCheck
import com.febinrukfan.newsfeed.utils.Resource

class NewsFeedActivity : AppCompatActivity() , NewsFeedTypeAdapter.OnItemClickListener{

    lateinit var viewModel: NewsFeedViewModel

    private lateinit var networkCheck: NetworkCheck

    lateinit var newsFeedAdapter: NewsFeedAdapter
    lateinit var newsFeedTypeAdapter: NewsFeedTypeAdapter

    val TAG = this.javaClass.simpleName

    private lateinit var binding: ActivityNewsFeedBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val newsFeedRepository = NewsFeedRepository(NewsFeedDatabase(this))
        val viewModelProviderFactory = NewsFeedViewModelProviderFactory(application,newsFeedRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsFeedViewModel::class.java)

        setupRecyclerView()
        newsFeedObserver() //observing news api when online
        newsFeedTypeObserver() //observing news type when online
        networkCheck()
        initializeFab()
        saveSelectedNews()



    }

    private fun networkCheck() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("No Internet Connection")
        builder.setMessage("Make sure you're connected to a Wi-Fi or mobile network and try again")
        val alertDialog = builder.create()

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok") { dialogInterface, _ ->

            viewModel.getAllNewsFeedResponseIem().observe(this, Observer { newsResposeItem ->
                newsFeedAdapter.differ.submitList(newsResposeItem)
            })

            dialogInterface.dismiss()
        }

        networkCheck = NetworkCheck(application)
        networkCheck.observe(this) { isConnected ->
            if (isConnected) {
                alertDialog.dismiss()
            } else {
                alertDialog.show()
            }
        }
    }

    private fun saveSelectedNews() {
        newsFeedAdapter.setOnItemClickListener {

            val sharedPreferences = application.getSharedPreferences("Saved_Ids", Context.MODE_PRIVATE)
            if(sharedPreferences.getInt("${it.id}",0) != it.id){
                viewModel.saveNewsFeedResponseItem(it)   // Saving selected news to database

                Log.e(TAG, "¬" + " save")
            }else{
                viewModel.deleteAllNewsFeedResponseIem(it)   // Saving selected news to database
                Log.e(TAG, "inzide" + " delete")
            }


        }
    }

    private fun newsFeedTypeObserver() {
        viewModel.newsFeedType.observe(this, Observer { response ->
            when(response) {
                is Resource.Success -> {

                    response.data?.let { newsResponse ->

                        newsFeedTypeAdapter.differ.submitList(newsResponse)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Toast.makeText(this, "An error occured: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                }
            }
        })
    }

    private fun newsFeedObserver() {
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
                        Toast.makeText(this, "An error occured: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun initializeFab() {
        binding.fabHome.setOnClickListener {
            viewModel.getNewsFeedTypes()
            viewModel.getNewsFeed()
        }
    }


    private fun setupRecyclerView() {
        newsFeedAdapter = NewsFeedAdapter(applicationContext)
        binding.rvNewFeed.apply {
            adapter = newsFeedAdapter
            layoutManager = LinearLayoutManager(this@NewsFeedActivity)

        }


        // passing item click listener call back to get on item click from adapter
        newsFeedTypeAdapter = NewsFeedTypeAdapter(this)
        binding.rvTypes.apply {
            adapter = newsFeedTypeAdapter
            layoutManager = LinearLayoutManager(this@NewsFeedActivity,LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    override fun onItemClick(type: String) {

        viewModel.getNewsFeedByType(type)

    }
}








