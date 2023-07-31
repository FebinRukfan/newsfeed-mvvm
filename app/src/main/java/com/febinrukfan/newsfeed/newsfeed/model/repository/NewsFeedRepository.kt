package com.febinrukfan.newsfeed.newsfeed.model.repository

import com.febinrukfan.newsfeed.newsfeed.model.remote.RetrofitInstance
import com.febinrukfan.newsfeed.newsfeed.model.local.db.NewsFeedDatabase
import com.febinrukfan.newsfeed.newsfeed.model.NewsFeedResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsFeedRepository(
    val db: NewsFeedDatabase)
{

    suspend fun getNewsFeed(pageNumber: Int) =
        RetrofitInstance.api.getNewsFeed(pageNumber)

    suspend fun getNewsFeedByType(type: String, pageNumber: Int) =
        RetrofitInstance.api.getNewsFeedByType(type,pageNumber)

    // OFFLINE
    suspend fun upsert(newsFeedResponseItem: NewsFeedResponseItem){
        withContext(Dispatchers.IO){
            db.getNewsFeedDao().upsert(newsFeedResponseItem)
        }
    }
    fun getAllNewsFeed() = db.getNewsFeedDao().getAllNewsFeed()

    suspend fun deleteAllNewsFeed(id: Int){
        withContext(Dispatchers.IO){
            db.getNewsFeedDao().deleteAllNewsFeed(id)
        }
    }




}