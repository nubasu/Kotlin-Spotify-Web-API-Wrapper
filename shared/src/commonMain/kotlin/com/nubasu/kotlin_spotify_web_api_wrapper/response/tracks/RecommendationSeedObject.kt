package com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks

data class RecommendationSeedObject(
    val afterFilteringSize: Int,
    val afterRelinkingSize: Int,
    val href: String,
    val id: String,
    val initialPoolSize: Int,
    val type: String,
)
