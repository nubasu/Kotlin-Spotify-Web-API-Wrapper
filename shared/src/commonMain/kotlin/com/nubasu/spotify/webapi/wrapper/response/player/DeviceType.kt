package com.nubasu.spotify.webapi.wrapper.response.player

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Spotify Connect device type. */
@Serializable
enum class DeviceType {
    @SerialName("Computer")
    COMPUTER,

    @SerialName("Tablet")
    TABLET,

    @SerialName("Smartphone")
    SMARTPHONE,

    @SerialName("Speaker")
    SPEAKER,

    @SerialName("TV")
    TV,

    @SerialName("AVR")
    AVR,

    @SerialName("STB")
    STB,

    @SerialName("AudioDongle")
    AUDIO_DONGLE,

    @SerialName("GameConsole")
    GAME_CONSOLE,

    @SerialName("CastAudio")
    CAST_AUDIO,

    @SerialName("CastVideo")
    CAST_VIDEO,

    @SerialName("Automobile")
    AUTOMOBILE,

    @SerialName("Unknown")
    UNKNOWN,
}
