package com.nubasu.spotify.webapi.wrapper

interface DesktopCallbackCoordinator {
    /**
     * Executes beginSession.
     *
     * @return The resulting String? fun consumeCallbackUri(): String? fun close() value.
     */
    fun beginSession(): String?
    /**
     * Executes consumeCallbackUri.
     *
     * @return The resulting String? fun close() value.
     */
    fun consumeCallbackUri(): String?
    /**
     * Executes close.
     */
    fun close()
}
