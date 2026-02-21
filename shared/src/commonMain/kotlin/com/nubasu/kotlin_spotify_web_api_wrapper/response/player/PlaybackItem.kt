package com.nubasu.spotify.webapi.wrapper.response.player

import kotlinx.serialization.Serializable

@Serializable(with = PlaybackItemSerializer::class)
interface PlaybackItem