package com.mehmetpeker.glancereddit.data

import kotlinx.serialization.Serializable

@Serializable
data class RedditItemModel(
    val title: String,
    val selfText: String,
    val url: String,
)

@Serializable
data class RedditPreferences(
    val redditList: List<RedditItemModel> = listOf()
)