package com.nubasu.kotlin_spotify_web_api_wrapper.response.player

import kotlinx.serialization.Serializable

@Serializable(with = PlaybackItemSerializer::class)
interface PlaybackItem