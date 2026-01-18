package com.nubasu.kotlin_spotify_web_api_wrapper.response.chapters

import com.nubasu.kotlin_spotify_web_api_wrapper.response.audiobooks.ResumePoint
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.Restrictions
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimplifiedChapterObject(
    @Deprecated("")
    @SerialName("audio_preview_url")
    val audioPreviewUrl: String? = null,
    @SerialName("available_markets")
    val availableMarkets: List<String> ?= null,
    @SerialName("chapter_number")
    val chapterNumber: Int,
    val description: String,
    @SerialName("html_description")
    val htmlDescription: String,
    @SerialName("duration_ms")
    val durationMs: Int,
    val explicit: Boolean,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    @SerialName("is_playable")
    val isPlayable: Boolean,
    val languages: List<String>,
    val name: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("release_date_precision")
    val releaseDatePrecision: String,
    @SerialName("resume_point")
    val resumePoint: ResumePoint? = null,
    val type: String,
    val uri: String,
    val restrictions: Restrictions? = null,
)
