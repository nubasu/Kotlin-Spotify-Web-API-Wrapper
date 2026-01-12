package com.nubasu.kotlin_spotify_web_api_wrapper.response.albums

data class UsersSavedAlbums(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<SavedAlbumObject>
)