package com.febinrukfan.newsfeed.repository

import com.febinrukfan.newsfeed.api.RetrofitInstance

class NewsFeedRepository {

    suspend fun getNewsFeed(pageNumber: Int) =
        RetrofitInstance.api.getNewsFeed(pageNumber)

   suspend fun getNewsFeedTypes(pageNumber: Int) =
        RetrofitInstance.api.getNewsFeed(pageNumber)

    suspend fun getNewsFeedByType(type: String, pageNumber: Int) =
        RetrofitInstance.api.getNewsFeedByType(type,pageNumber)



}