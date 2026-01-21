package com.nubasu.kotlin_spotify_web_api_wrapper.response.episodes

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.CopyrightObject
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Show(
    @SerialName("available_markets")
    val availableMarkets: List<String>,
    val copyrights: List<CopyrightObject>,
    val description: String,
    @SerialName("html_description")
    val htmlDescription: String,
    val explicit: Boolean,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    @SerialName("is_external_hosted")
    val isExternalHosted: Boolean,
    val languages: List<String>,
    @SerialName("media_type")
    val mediaType: String,
    val name: String,
    val publisher: String,
    val type: String,
    val uri: String,
    @SerialName("total_episodes")
    val totalEpisodes: Int,
)
