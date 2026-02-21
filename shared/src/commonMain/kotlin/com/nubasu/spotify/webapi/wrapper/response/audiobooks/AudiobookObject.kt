package com.nubasu.spotify.webapi.wrapper.response.audiobooks

import com.nubasu.spotify.webapi.wrapper.response.common.CopyrightObject
import com.nubasu.spotify.webapi.wrapper.response.common.ExternalUrls
import com.nubasu.spotify.webapi.wrapper.response.common.ImageObject

data class AudiobookObject(
    val authors: List<AuthorObject>,
    val availableMarkets: List<String>,
    val copyrights: List<CopyrightObject>,
    val description: String,
    val htmlDescription: String,
    val edition: String,
    val explicit: Boolean,
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    val languages: List<String>,
    val mediaType: String,
    val name: String,
    val narrators: List<NarratorObject>,
    val publisher: String,
    val type: String,
    val uri: String,
    val totalChapters: Int,
    val chapters: Chapters,
)
