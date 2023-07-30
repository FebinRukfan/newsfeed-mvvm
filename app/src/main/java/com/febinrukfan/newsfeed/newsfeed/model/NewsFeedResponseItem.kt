package com.febinrukfan.newsfeed.newsfeed.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "newsfeed"
)
data class NewsFeedResponseItem(

    @PrimaryKey(autoGenerate = true)
    val idLocal: Int? = null,
    val id: Int,
    val title: String,
    val type: String,
    val typeAttributes: TypeAttributes,
    val readablePublishedAt: String
    )