package com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationSeedObject(
    @SerialName("after_filtering_size")
    val afterFilteringSize: Int? = null,
    @SerialName("after_relinking_size")
    val afterRelinkingSize: Int? = null,
    val href: String? = null,
    val id: String? = null,
    @SerialName("initial_pool_size")
    val initialPoolSize: Int? = null,
    val type: String? = null,
)
