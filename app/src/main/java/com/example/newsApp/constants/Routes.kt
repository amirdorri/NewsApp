package com.example.newsApp.constants

import kotlinx.serialization.Serializable


@Serializable
object HomeScreen

@Serializable
data class NewsArticleScreen(
    val url : String
)