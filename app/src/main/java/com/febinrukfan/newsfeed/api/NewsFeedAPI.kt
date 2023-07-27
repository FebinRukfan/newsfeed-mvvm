package com.febinrukfan.newsfeed.api

import com.febinrukfan.newsfeed.models.NewsFeedResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsFeedAPI {

    @GET("v1/items?lineupSlug=news")
    suspend fun getNewsFeed(
        @Query("page")
        pageNumber: Int = 1
    ): Response<List<NewsFeedResponseItem>>


}