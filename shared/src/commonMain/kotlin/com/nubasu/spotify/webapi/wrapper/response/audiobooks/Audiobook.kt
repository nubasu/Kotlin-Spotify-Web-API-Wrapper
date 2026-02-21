package com.nubasu.spotify.webapi.wrapper.response.audiobooks

import com.nubasu.spotify.webapi.wrapper.response.common.CopyrightObject
import com.nubasu.spotify.webapi.wrapper.response.common.ExternalUrls
import com.nubasu.spotify.webapi.wrapper.response.common.ImageObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Audiobook(
    val authors: List<AuthorObject>,
    @SerialName("available_markets")
    val availableMarkets: List<String>,
    val copyrights: List<CopyrightObject>,
    val description: String,
    @SerialName("html_description")
    val htmlDescription: String,
    val edition: String? = null,
    val explicit: Boolean,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    val languages: List<String>,
    @SerialName("media_type")
    val mediaType: String,
    val name: String,
    val narrators: List<NarratorObject>,
    val publisher: String,
    val type: String,
    val uri: String,
    @SerialName("total_chapters")
    val totalChapters: Int,
    val chapters: Chapters,
)
