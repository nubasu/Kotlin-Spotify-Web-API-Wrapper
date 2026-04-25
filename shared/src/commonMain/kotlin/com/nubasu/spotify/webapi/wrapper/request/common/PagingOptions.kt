package com.nubasu.spotify.webapi.wrapper.request.common

data class PagingOptions(
    val limit: Int? = null,
    val offset: Int? = null,
) {
    init {
        limit?.let { require(it in 1..50) { "limit must be between 1 and 50, was $it" } }
        offset?.let { require(it >= 0) { "offset must be non-negative, was $it" } }
    }
}
