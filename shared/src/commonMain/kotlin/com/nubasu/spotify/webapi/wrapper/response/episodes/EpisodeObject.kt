package com.nubasu.spotify.webapi.wrapper.response.episodes

import com.nubasu.spotify.webapi.wrapper.response.player.PlaybackItem
import com.nubasu.spotify.webapi.wrapper.response.audiobooks.ResumePoint
import com.nubasu.spotify.webapi.wrapper.response.common.ExternalUrls
import com.nubasu.spotify.webapi.wrapper.response.common.ImageObject
import com.nubasu.spotify.webapi.wrapper.response.common.Restrictions

data class EpisodeObject(
    val audioPreviewUrl: String,
    val description: String,
    val htmlDescription: String,
    val durationMs: Int,
    val explicit: Boolean,
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    val isExternallyHosted: Boolean,
    val isPlayable: Boolean,
    @Deprecated("")
    val language: String,
    val languages: List<String>,
    val name: String,
    val releaseDate: String,
    val releaseDatePrecision: String,
    val resumePoint: ResumePoint,
    val type: String,
    val uri: String,
    val restrictions: Restrictions,
    val show: Show,
) : PlaybackItem