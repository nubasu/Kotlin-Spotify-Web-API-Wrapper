package com.nubasu.kotlin_spotify_web_api_wrapper.response.episodes

import com.nubasu.kotlin_spotify_web_api_wrapper.response.audiobooks.ResumePoint
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.Restrictions
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    @Deprecated("")
    @SerialName("audio_preview_url")
    val audioPreviewUrl: String? = null,
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
    @SerialName("is_externally_hosted")
    val isExternallyHosted: Boolean,
    @SerialName("is_playable")
    val isPlayable: Boolean,
    @Deprecated("")
    val language: String? = null,
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
    val show: Show,
)
