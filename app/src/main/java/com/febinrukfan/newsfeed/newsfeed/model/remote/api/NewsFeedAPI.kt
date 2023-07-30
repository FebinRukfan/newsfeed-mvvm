package com.febinrukfan.newsfeed.newsfeed.model.remote.api

import com.febinrukfan.newsfeed.newsfeed.model.NewsFeedResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsFeedAPI {

    @GET("v1/items?lineupSlug=news")
    suspend fun getNewsFeed(
        @Query("page")
        pageNumber: Int = 1
    ): Response<List<NewsFeedResponseItem>>

    @GET("v1/items?lineupSlug=news")
    suspend fun getNewsFeedTypes(
        @Query("page")
        pageNumber: Int = 1
    ): Response<List<NewsFeedResponseItem>>

    @GET("v1/items?lineupSlug=news")
    suspend fun getNewsFeedByType(
        @Query("type")
        type: String,
        @Query("page")
        pageNumber: Int = 1
    ): Response<List<NewsFeedResponseItem>>


}