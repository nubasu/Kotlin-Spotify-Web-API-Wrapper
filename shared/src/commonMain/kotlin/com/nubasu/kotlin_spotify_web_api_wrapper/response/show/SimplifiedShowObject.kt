package com.nubasu.kotlin_spotify_web_api_wrapper.response.show

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.CopyrightObject
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject

data class SimplifiedShowObject(
    val availableMarkets: List<String>,
    val copyrights: List<CopyrightObject>,
    val description: String,
    val htmlDescription: String,
    val explicit: Boolean,
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    val isExternalHosted: Boolean,
    val languages: List<String>,
    val mediaType: String,
    val name: String,
    val publisher: String,
    val type: String,
    val uri: String,
    val totalEpisodes: Int,
)