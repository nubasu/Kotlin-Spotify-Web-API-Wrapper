package com.nubasu.kotlin_spotify_web_api_wrapper.response.chapters

import com.nubasu.kotlin_spotify_web_api_wrapper.response.audiobooks.Audiobook
import com.nubasu.kotlin_spotify_web_api_wrapper.response.audiobooks.ResumePoint
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.Restrictions

data class ChapterObject(
    val audioPreviewUrl: String,
    val availableMarkets: List<String>,
    val chapterNumber: Int,
    val description: String,
    val htmlDescription: String,
    val durationMs: Int,
    val explicit: Boolean,
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    val isPlayable: Boolean,
    val languages: List<String>,
    val name: String,
    val releaseDate: String,
    val releaseDatePrecision: String,
    val resumePoint: ResumePoint,
    val type: String,
    val uri: String,
    val restrictions: Restrictions,
    val audiobook: Audiobook,
)