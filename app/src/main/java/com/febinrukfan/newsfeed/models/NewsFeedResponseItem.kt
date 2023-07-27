package com.febinrukfan.newsfeed.models

data class NewsFeedResponseItem(
    val title: String,
    val type: String,
    val typeAttributes: TypeAttributes,
    val readablePublishedAt: String,
)