package com.febinrukfan.newsfeed.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.febinrukfan.newsfeed.NewsFeedApplication
import com.febinrukfan.newsfeed.models.NewsFeedResponseItem
import com.febinrukfan.newsfeed.repository.NewsFeedRepository
import com.febinrukfan.newsfeed.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class NewsFeedViewModel(
    app : Application,
    val newsFeedRespository : NewsFeedRepository
) : AndroidViewModel(app) {

    val TAG = this.javaClass.simpleName

    val newsFeed: MutableLiveData<Resource<List<NewsFeedResponseItem>>> = MutableLiveData()
    var newsFeedPage = 1

    val newsFeedType: MutableLiveData<Resource<List<NewsFeedResponseItem>>> = MutableLiveData()
    var newsFeedTypePage = 1


    init {
        getNewsFeed()
        getNewsFeedTypes()
    }

    fun getNewsFeed() = viewModelScope.launch {

        try {
            if(checkInternetConnection()){
                newsFeed.postValue(Resource.Loading())

                val response = newsFeedRespository.getNewsFeed( newsFeedPage)

                newsFeed.postValue(handleNewsFeedResponse(response))
            }
            else{
                newsFeed.postValue(Resource.Error("No internet connection"))

            }

        }
        catch (t: Throwable){
            when(t) {
                is IOException -> newsFeed.postValue(Resource.Error("Network Failure"))
                else -> newsFeed.postValue(Resource.Error("Conversion Error"))
            }
        }

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

    // get news field types only
    fun getNewsFeedTypes() = viewModelScope.launch {

        try {

            if(checkInternetConnection()){
                newsFeedType.postValue(Resource.Loading())

                val response = newsFeedRespository.getNewsFeed( newsFeedTypePage)

                newsFeedType.postValue(handleNewsFeedTypesResponse(response))

            }else{
                newsFeedType.postValue(Resource.Error("No internet connection"))

            }

        }catch (t: Throwable){
            when(t) {
                is IOException -> newsFeedType.postValue(Resource.Error("Network Failure"))
                else -> newsFeedType.postValue(Resource.Error("Conversion Error"))
            }
        }

    }


    private fun handleNewsFeedTypesResponse(response: Response<List<NewsFeedResponseItem>>): Resource<List<NewsFeedResponseItem>> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->

                Log.v(TAG,"responz" + resultResponse)

                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

   // To-do ~ get rid of this below methode when you have a methode to call all news from activity
 fun getNewsFeedByType(type: String) = viewModelScope.launch {

        try {
            if(checkInternetConnection()){
                newsFeed.postValue(Resource.Loading())

                val response = newsFeedRespository.getNewsFeedByType( type, newsFeedPage)

                newsFeed.postValue(handleNewsFeedResponseByType(response))
            }else {
                newsFeed.postValue(Resource.Error("No internet connection"))
            }


        } catch (t: Throwable){
            when(t) {
                is IOException -> newsFeed.postValue(Resource.Error("Network Failure"))
                else -> newsFeed.postValue(Resource.Error("Conversion Error"))
            }
        }

    }


 private fun handleNewsFeedResponseByType(response: Response<List<NewsFeedResponseItem>>): Resource<List<NewsFeedResponseItem>> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->

                Log.v(TAG,"responz" + resultResponse)

                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
 }

    private fun checkInternetConnection()
    : Boolean {
        val connectivityManager = getApplication<NewsFeedApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }



}


