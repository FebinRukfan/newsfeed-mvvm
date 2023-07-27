package com.febinrukfan.newsfeed.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.febinrukfan.newsfeed.models.NewsFeedResponseItem
import com.febinrukfan.newsfeed.repository.NewsFeedRepository
import com.febinrukfan.newsfeed.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class NewsFeedViewModel(
    app : Application,
    val newsFeedRespository : NewsFeedRepository
) : AndroidViewModel(app) {

    val TAG = this.javaClass.simpleName

    val newsFeed: MutableLiveData<Resource<List<NewsFeedResponseItem>>> = MutableLiveData()
    var newsFeedPage = 1

    init {
        getNewsFeed()
    }

    fun getNewsFeed() = viewModelScope.launch {
        newsFeed.postValue(Resource.Loading())

        val response = newsFeedRespository.getNewsFeed( newsFeedPage)

        newsFeed.postValue(handleNewsFeedResponse(response))
    }

    private fun handleNewsFeedResponse(response: Response<List<NewsFeedResponseItem>>): Resource<List<NewsFeedResponseItem>> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->

                Log.v(TAG,"responz" + resultResponse)

                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


}


