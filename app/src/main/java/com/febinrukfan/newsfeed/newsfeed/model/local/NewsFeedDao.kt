package com.febinrukfan.newsfeed.newsfeed.model.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.febinrukfan.newsfeed.newsfeed.model.NewsFeedResponseItem

@Dao
interface NewsFeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(newsFeedResponseItem: NewsFeedResponseItem): Long

    @Query("SELECT * FROM newsfeed ORDER BY readablePublishedAt DESC")
    fun getAllNewsFeed(): LiveData<List<NewsFeedResponseItem>>

    @Query("DELETE  FROM newsfeed WHERE id = :id")
    fun deleteAllNewsFeed(id: Int)

}
