package com.nubasu.spotify.webapi.wrapper.utils

import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import io.ktor.http.URLBuilder

/**
 * Appends `limit` and `offset` query parameters from [pagingOptions] to this URL builder.
 *
 * Only parameters with non-null values are appended.
 * Use this helper for endpoints that use offset-based paging.
 * For cursor-based paging (`before`/`after`), append those parameters manually.
 */
internal fun URLBuilder.applyLimitOffsetPaging(pagingOptions: PagingOptions) {
    pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
    pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
}
